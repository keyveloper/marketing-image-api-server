package org.example.marketingimageapiserver.service

import io.github.oshai.kotlinlogging.KotlinLogging
import org.apache.tika.Tika
import org.example.marketingimageapiserver.dto.AdvertisementImageMetadata
import org.example.marketingimageapiserver.dto.AdvertisementImageMetadataEntity
import org.example.marketingimageapiserver.dto.AdvertisementImageMetadataWithUrl
import org.example.marketingimageapiserver.dto.ConnectAdvertisementResult
import org.example.marketingimageapiserver.dto.DeleteAdImageResult
import org.example.marketingimageapiserver.dto.UploadAdvertisementImageApiRequest
import org.example.marketingimageapiserver.dto.SaveAdvertisementImageResult
import org.example.marketingimageapiserver.exception.NotFoundAdImageMetaDataException
import org.example.marketingimageapiserver.exception.S3DeleteException
import org.example.marketingimageapiserver.exception.S3UploadException
import org.example.marketingimageapiserver.repository.AdvertisementImageMetaRepository
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest
import software.amazon.awssdk.services.s3.model.GetObjectRequest
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import software.amazon.awssdk.services.s3.model.S3Exception
import software.amazon.awssdk.services.s3.presigner.S3Presigner
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest
import java.time.Duration
import java.util.*

@Service
class AdvertisementImageService(
    private val advertisementImageMetaRepository: AdvertisementImageMetaRepository,
    private val s3Client: S3Client,
    private val s3Presigner: S3Presigner,
) {
    private val looger = KotlinLogging.logger {}
    private val tika = Tika()

    fun saveAdvertisementImage(
        meta: UploadAdvertisementImageApiRequest,
        file: MultipartFile
    ): SaveAdvertisementImageResult {

        return transaction {
            // Extract file metadata from MultipartFile using Tika
            val fileSize = file.size
            val originalFileName = file.originalFilename

            // Use Tika to detect the actual content type from file content
            val detectedContentType = tika.detect(file.inputStream, originalFileName)
            val contentType = detectedContentType ?: file.contentType ?: "application/octet-stream"

            // Generate unique S3 key
            val s3Key = "advertisement-images/${UUID.randomUUID()}-${UUID.randomUUID()}"
            val bucketName = "marketing-image-bucket"
            var s3UploadSuccessful = false

            try {
                val logger = KotlinLogging.logger {}
                // Upload file to S3
                val putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(s3Key)
                    .contentType(contentType)
                    .contentLength(fileSize)
                    .build()

                val response = s3Client.putObject(
                    putObjectRequest,
                    RequestBody.fromInputStream(file.inputStream, fileSize)
                )

                logger.info { "response: ${response}" }
                s3UploadSuccessful = true

                val createdId = advertisementImageMetaRepository.saveAdvertisementImageMetadata(
                    AdvertisementImageMetadata.of(
                        advertisementId = -1, // save as draft!
                        advertisementDraftId = meta.advertisementDraftId,
                        writerId = meta.writerId,
                        isThumbnail = meta.isThumbnail,
                        originalFileName = originalFileName,
                        contentType = contentType,
                        size = fileSize,
                        bucketName = bucketName,
                        s3Key = s3Key
                    )
                )

                SaveAdvertisementImageResult.of(
                    id = createdId,
                    s3Key = s3Key,
                    bucketName = bucketName,
                    contentType = contentType,
                    size = fileSize,
                    originalFileName = originalFileName
                )
            } catch (e: S3Exception) {
              throw S3UploadException(
                  logics = "AdvertisementImageService.saveAdvertisementImage",
                  message = e.message?: "S3 file uploading failed"
                  )
            } catch (e: Exception) {
                // Rollback: Delete S3 object if it was successfully uploaded
                if (s3UploadSuccessful) {
                    try {
                        val deleteObjectRequest = DeleteObjectRequest.builder()
                            .bucket(bucketName)
                            .key(s3Key)
                            .build()

                        s3Client.deleteObject(deleteObjectRequest)
                        looger.info { "Successfully deleted S3 object during rollback: $s3Key" }
                    } catch (deleteException: Exception) {
                        looger.error(deleteException) {
                            "Failed to delete S3 object during rollback: $s3Key"
                        }
                    }
                }
                throw RuntimeException("Failed to save advertisement image: ${e.message}", e)
            }
        }
    }

    fun getAdvertisementImageByAdId(
        advertisementId: Long
    ): List<AdvertisementImageMetadataWithUrl> {
        return transaction {

            // 1. Find advertisement image metadata by advertisementId
            val advertisementImageMetadataEntities: List<AdvertisementImageMetadataEntity> =
                advertisementImageMetaRepository.findAdvertisementImageMetaDataByAdvertisementId(advertisementId)

            // 2. Make S3 presigned URL request using the key
            advertisementImageMetadataEntities.map { entity ->

                val getObjectRequest = GetObjectRequest.builder()
                    .bucket(entity.bucketName)
                    .key(entity.s3Key)
                    .build()

                val presignRequest = GetObjectPresignRequest.builder()
                    .signatureDuration(Duration.ofMinutes(15)) // URL expires in 15 minutes
                    .getObjectRequest(getObjectRequest)
                    .build()

                val presignedUrl = s3Presigner.presignGetObject(presignRequest).url().toString()
                AdvertisementImageMetadataWithUrl.of(
                    advertisementId = entity.advertisementId,
                    writerId = entity.writerId,
                    presignedUrl = presignedUrl,
                    bucketName = entity.bucketName,
                    s3Key = entity.s3Key,
                    contentType = entity.contentType,
                    size = entity.size,
                    originalFileName = entity.originalFileName,
                    isThumbnail = entity.isThumbnail
                )
            }
        }
    }

    fun deleteByMetaId(
        imageMetaId: Long
    ): DeleteAdImageResult {
        return transaction {
            val imageMetadataEntity = advertisementImageMetaRepository.findByImageMetaId(imageMetaId)
                ?: throw NotFoundAdImageMetaDataException(
                    metaId = imageMetaId,
                    advertisementId = null,
                    logics = "AdvertisementImageService.deleteByMetaId",
                )

            try {
                val bucketName = imageMetadataEntity.bucketName
                val s3Key = imageMetadataEntity.s3Key

                val deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(s3Key)
                    .build()

                s3Client.deleteObject(deleteObjectRequest)
                DeleteAdImageResult.of(
                    imageMetaId = imageMetaId,
                    size = imageMetadataEntity.size,
                    contentType = imageMetadataEntity.contentType,
                    s3BucketKey = s3Key,
                )
            } catch(e: S3Exception) {
                throw S3DeleteException(
                    logics = "AdvertisementImageService.deleteByMetaId",
                    message = e.message?: "S3 file deletion failed"
                )
            }
        }
    }

    fun getAllAdvertisementImages(): String {
        return "Get all advertisement images from service"
    }

    fun connectAdvertisementId(draftId: Long, advertisementId: Long): ConnectAdvertisementResult {
        return transaction {
            val imageMetadataEntities = advertisementImageMetaRepository.findByDraftId(draftId)

            if (imageMetadataEntities.isEmpty()) {
                throw NotFoundAdImageMetaDataException(
                    metaId = null,
                    advertisementId = null,
                    logics = "AdvertisementImageService.connectAdvertisementId",
                )
            }

            val connectedS3BucketKeys = mutableListOf<String>()
            imageMetadataEntities.forEach { entity ->
                entity.advertisementId = advertisementId
                connectedS3BucketKeys.add(entity.s3Key)
            }

            ConnectAdvertisementResult(
                imageMetadataEntities.size,
                connectedS3BucketKeys

            )
        }
    }

}
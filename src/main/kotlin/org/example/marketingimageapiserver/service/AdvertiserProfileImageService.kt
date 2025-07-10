package org.example.marketingimageapiserver.service

import io.github.oshai.kotlinlogging.KotlinLogging
import org.apache.tika.Tika
import org.example.marketingimageapiserver.dto.*
import org.example.marketingimageapiserver.exception.S3UploadException
import org.example.marketingimageapiserver.repository.AdvertiserProfileImageMetaRepository
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
class AdvertiserProfileImageService(
    private val advertiserProfileImageMetaRepository: AdvertiserProfileImageMetaRepository,
    private val s3Client: S3Client,
    private val s3Presigner: S3Presigner,
) {
    private val logger = KotlinLogging.logger {}
    private val tika = Tika()

    fun saveAdvertiserProfileImage(
        meta: UploadAdvertiserProfileImageApiRequest,
        file: MultipartFile
    ): SaveAdvertiserProfileImageResult {

        return transaction {
            val fileSize = file.size
            val originalFileName = file.originalFilename

            val detectedContentType = tika.detect(file.inputStream, originalFileName)
            val contentType = detectedContentType ?: file.contentType ?: "application/octet-stream"

            val userType = meta.userType
            val imageType = meta.profileImageType
            val s3Key = "${userType.name.lowercase()}/${imageType.name.lowercase()}/${UUID.randomUUID()}-${UUID.randomUUID()}"
            val bucketName = "marketing-user-profile-image"
            var s3UploadSuccessful = false

            try {
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

                logger.info { "S3 upload response: ${response}" }
                s3UploadSuccessful = true

                val createdId = advertiserProfileImageMetaRepository.saveAdvertiserProfileImageMetadata(
                    AdvertiserProfileImageMetadata.of(
                        userId = meta.userId,
                        userType= meta.userType,
                        profileImageType = meta.profileImageType,
                        advertiserProfileDraftId = meta.advertiserProfileDraftId,
                        imageType = meta.profileImageType,
                        originalFileName = originalFileName,
                        contentType = contentType,
                        size = fileSize,
                        bucketName = bucketName,
                        s3Key = s3Key
                    )
                )

                SaveAdvertiserProfileImageResult.of(
                    id = createdId,
                    s3Key = s3Key,
                    bucketName = bucketName,
                    contentType = contentType,
                    size = fileSize,
                    originalFileName = originalFileName
                )
            } catch (e: S3Exception) {
                throw S3UploadException(
                    logics = "AdvertiserProfileImageService.saveAdvertiserProfileImage",
                    message = e.message ?: "S3 file uploading failed"
                )
            } catch (e: Exception) {
                if (s3UploadSuccessful) {
                    try {
                        val deleteObjectRequest = DeleteObjectRequest.builder()
                            .bucket(bucketName)
                            .key(s3Key)
                            .build()

                        s3Client.deleteObject(deleteObjectRequest)
                        logger.info { "Successfully deleted S3 object during rollback: $s3Key" }
                    } catch (deleteException: Exception) {
                        logger.error(deleteException) {
                            "Failed to delete S3 object during rollback: $s3Key"
                        }
                    }
                }
                throw RuntimeException("Failed to save advertiser profile image: ${e.message}", e)
            }
        }
    }

    fun getAdvertiserProfileImageByUserId(userId: String, userType: String): List<AdvertiserProfileImageMetadataWithUrl> {
        return transaction {
            val advertiserProfileImageMetadataEntities: List<AdvertiserProfileImageMetadataEntity> =
                advertiserProfileImageMetaRepository.findAdvertiserProfileImageByUserId(userId)

            advertiserProfileImageMetadataEntities.map { entity ->
                val getObjectRequest = GetObjectRequest.builder()
                    .bucket(entity.bucketName)
                    .key(entity.s3Key)
                    .build()

                val presignRequest = GetObjectPresignRequest.builder()
                    .signatureDuration(Duration.ofMinutes(15))
                    .getObjectRequest(getObjectRequest)
                    .build()

                val presignedUrl = s3Presigner.presignGetObject(presignRequest).url().toString()
                AdvertiserProfileImageMetadataWithUrl.of(
                    userId = entity.userId,
                    userType = userType,
                    imageType = entity.imageType,
                    presignedUrl = presignedUrl,
                    bucketName = entity.bucketName,
                    s3Key = entity.s3Key,
                    contentType = entity.contentType,
                    size = entity.size,
                    originalFileName = entity.originalFileName
                )
            }
        }
    }
}

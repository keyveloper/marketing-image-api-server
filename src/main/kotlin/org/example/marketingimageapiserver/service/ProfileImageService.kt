package org.example.marketingimageapiserver.service

import io.github.oshai.kotlinlogging.KotlinLogging
import org.example.marketingimageapiserver.dto.UploadUserProfileImageApiRequest
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import software.amazon.awssdk.services.s3.model.GetObjectRequest
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest
import software.amazon.awssdk.services.s3.model.S3Exception
import software.amazon.awssdk.services.s3.presigner.S3Presigner
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest
import org.apache.tika.Tika
import org.example.marketingimageapiserver.dto.DeleteProfileImageResult
import org.example.marketingimageapiserver.dto.ProfileImageMetadataWithUrl
import org.example.marketingimageapiserver.dto.ProfileImageMetadata
import org.example.marketingimageapiserver.dto.ProfileImageMetadataEntity
import org.example.marketingimageapiserver.dto.SaveProfileImageResult
import org.example.marketingimageapiserver.enums.UserType
import org.example.marketingimageapiserver.exception.NotFoundProfileImageMetaDataException
import org.example.marketingimageapiserver.exception.S3DeleteException
import org.example.marketingimageapiserver.exception.S3UploadException
import org.example.marketingimageapiserver.repository.ProfileImageMetaRepository
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.Duration
import java.util.*

@Service
class ProfileImageService(
    private val profileImageMetaRepository: ProfileImageMetaRepository,
    private val s3Client: S3Client,
    private val s3Presigner: S3Presigner,
) {
    private val looger = KotlinLogging.logger {}
    private val tika = Tika()

    fun saveProfileImage(
        meta: UploadUserProfileImageApiRequest,
        file: MultipartFile
    ): SaveProfileImageResult {

        return transaction {
            // Extract file metadata from MultipartFile using Tika
            val fileSize = file.size
            val originalFileName = file.originalFilename

            // Use Tika to detect the actual content type from file content
            val detectedContentType = tika.detect(file.inputStream, originalFileName)
            val contentType = detectedContentType ?: file.contentType ?: "application/octet-stream"

            // Generate unique S3 key
            val s3Key = "profile-images/${UUID.randomUUID()}-${UUID.randomUUID()}"
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

                val createdId = profileImageMetaRepository.saveProfileImageMetadata(
                    ProfileImageMetadata.of(
                        userType = meta.userType,
                        userId = meta.userId,
                        profileImageType = meta.profileImageType,
                        originalFileName = originalFileName,
                        contentType = contentType,
                        size = fileSize,
                        bucketName = bucketName,
                        s3Key = s3Key
                    )
                )

                SaveProfileImageResult.of(
                    id = createdId,
                    s3Key = s3Key,
                    bucketName = bucketName,
                    contentType = contentType,
                    size = fileSize,
                    originalFileName = originalFileName
                )
            } catch (e: S3Exception) {
              throw S3UploadException(
                  logics = "ProfileImageService.saveProfileImage",
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
                throw RuntimeException("Failed to save profile image: ${e.message}", e)
            }
        }
    }

    fun getProfileImage(
        userId: Long,
        userType: UserType
    ): List<ProfileImageMetadataWithUrl> {
        return transaction {
            // 1. Find bucket-key from profile-image-metadata by userId and userType

            val profileImageMetaDataEntities: List<ProfileImageMetadataEntity> =
                profileImageMetaRepository.findProfileImageMetaDataByUserInfo(userId, userType)


            // 2. Make S3 presigned URL request using the key
            profileImageMetaDataEntities.map { entity ->
                val getObjectRequest = GetObjectRequest.builder()
                    .bucket(entity.bucketName)
                    .key(entity.s3Key)
                    .build()

                val presignRequest = GetObjectPresignRequest.builder()
                    .signatureDuration(Duration.ofMinutes(15)) // URL expires in 15 minutes
                    .getObjectRequest(getObjectRequest)
                    .build()

                val presignedUrl = s3Presigner.presignGetObject(presignRequest).url().toString()
                ProfileImageMetadataWithUrl.of(
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

    fun deleteByImageMetaId(
        imageMetaId: Long
    ): DeleteProfileImageResult {
        return transaction {
            val imageMetadataEntity = profileImageMetaRepository.findByImageMetaId(imageMetaId)
                ?: throw NotFoundProfileImageMetaDataException(
                    metaId = imageMetaId,
                    logics = "ProfileImageService.deleteProfileImageMetaData",
                    userType = null,
                    userId = null,
                )

            try {
                val bucketName = imageMetadataEntity.bucketName
                val s3Key = imageMetadataEntity.s3Key

                val deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(s3Key)
                    .build()

                s3Client.deleteObject(deleteObjectRequest)
                DeleteProfileImageResult.of(
                    imageMetaId = imageMetaId,
                    size = imageMetadataEntity.size,
                    contentType = imageMetadataEntity.contentType,
                    s3BucketKey = s3Key,
                )
            } catch(e: S3Exception) {
                throw S3DeleteException(
                    logics = "ProfileImageService.deleteProfileImageMetaData",
                    message = e.message?: "S3 file deletion failed"
                )
            }
        }
    }
}
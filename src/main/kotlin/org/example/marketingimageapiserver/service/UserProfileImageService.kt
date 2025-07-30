package org.example.marketingimageapiserver.service

import io.github.oshai.kotlinlogging.KotlinLogging
import org.apache.tika.Tika
import org.example.marketingimageapiserver.dto.*
import org.example.marketingimageapiserver.enums.UserType
import org.example.marketingimageapiserver.enums.ProfileImageType
import org.example.marketingimageapiserver.exception.DuplicateProfileImageTypeException
import org.example.marketingimageapiserver.exception.S3UploadException
import org.example.marketingimageapiserver.exception.UserProfileImageCountExceededException
import org.example.marketingimageapiserver.repository.UserProfileImageMetaRepository
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
class UserProfileImageService(
    private val userProfileImageMetaRepository: UserProfileImageMetaRepository,
    private val s3Client: S3Client,
    private val s3Presigner: S3Presigner,
) {
    private val logger = KotlinLogging.logger {}
    private val tika = Tika()

    fun saveUserProfileImageAndMetadata(
        meta: UploadUserProfileImageApiRequest,
        file: MultipartFile
    ): SaveUserProfileImageResult {

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

                val createdId = userProfileImageMetaRepository.saveUserProfileImageMetadata(
                    UserProfileImageMetadata.of(
                        userId = meta.userId,
                        userType= meta.userType,
                        profileImageType = meta.profileImageType,
                        profileDraftId = meta.userProfileDraftId,
                        imageType = meta.profileImageType,
                        originalFileName = originalFileName,
                        contentType = contentType,
                        size = fileSize,
                        bucketName = bucketName,
                        s3Key = s3Key
                    )
                )

                SaveUserProfileImageResult.of(
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

    fun changeProfileStatusToSave(entityId: Long, userId: UUID, userType: UserType): Int {
        return transaction {
            userProfileImageMetaRepository.changeDraftToSave(
                targetEntityId = entityId,
                targetUserId = userId,
                targetUserType = userType
            )
        }
    }

    fun getUserProfileImagesByUserId(
        userId: UUID
    ): List<UserProfileImageMetadataWithUrl> {
        return transaction {

            // 1. Find user profile image metadata by userId
            val userProfileImageMetadataEntities: List<UserProfileImageMetadataEntity> =
                userProfileImageMetaRepository.findAdvertiserProfileImageByUserId(userId)

            // Validation: size must be 2 or less
            if (userProfileImageMetadataEntities.size > 2) {
                throw UserProfileImageCountExceededException(
                    logics = "UserProfileImageService.getUserProfileImagesByUserId",
                    message = "User profile image count exceeded. Found ${userProfileImageMetadataEntities.size} images for userId=$userId. Maximum allowed is 2."
                )
            }

            // Validation: if size is 2, must have one BACKGROUND and one PROFILE (no duplicates)
            if (userProfileImageMetadataEntities.size == 2) {
                val profileTypes = userProfileImageMetadataEntities.map { it.profileImageType }
                val hasBackground = profileTypes.contains(ProfileImageType.BACKGROUND)
                val hasProfile = profileTypes.contains(ProfileImageType.PROFILE)

                if (!hasBackground || !hasProfile) {
                    throw DuplicateProfileImageTypeException(
                        logics = "UserProfileImageService.getUserProfileImagesByUserId",
                        message = "Duplicate profile image type found for userId=$userId. Each user can have only one BACKGROUND and one PROFILE image."
                    )
                }
            }

            // 2. Make S3 presigned URL request using the key
            userProfileImageMetadataEntities.map { entity ->

                val getObjectRequest = GetObjectRequest.builder()
                    .bucket(entity.bucketName)
                    .key(entity.s3Key)
                    .build()

                val presignRequest = GetObjectPresignRequest.builder()
                    .signatureDuration(Duration.ofMinutes(15)) // URL expires in 15 minutes
                    .getObjectRequest(getObjectRequest)
                    .build()

                val presignedUrl = s3Presigner.presignGetObject(presignRequest).url().toString()
                UserProfileImageMetadataWithUrl.of(
                    userId = entity.userId,
                    userType = entity.userType,
                    profileImageType = entity.profileImageType,
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

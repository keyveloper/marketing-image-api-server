package org.example.marketingimageapiserver.service

import io.github.oshai.kotlinlogging.KotlinLogging
import org.example.marketingimageapiserver.dto.MakeNewProfileImageRequest
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import software.amazon.awssdk.services.s3.model.GetObjectRequest
import software.amazon.awssdk.services.s3.presigner.S3Presigner
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest
import org.apache.tika.Tika
import org.example.marketingimageapiserver.dto.ProfileImageMetadataWithUrl
import org.example.marketingimageapiserver.dto.ProfileImageMetadata
import org.example.marketingimageapiserver.dto.SaveFileResult
import org.example.marketingimageapiserver.enums.UserType
import org.example.marketingimageapiserver.exception.NotFoundProfileImageMetaDataException
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

    fun createProfileImage(
        meta: MakeNewProfileImageRequest,
        file: MultipartFile
    ): SaveFileResult {

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

                SaveFileResult.of(
                    id = createdId,
                    s3Key = s3Key,
                    bucketName = bucketName,
                    contentType = contentType,
                    size = fileSize,
                    originalFileName = originalFileName
                )
            } catch (e: Exception) {
                throw RuntimeException("Failed to upload file to S3: ${e.message}", e)
                // delete S3 and metadata
            }
        }

    }

    fun getProfileImage(
        userId: Long,
        userType: UserType
    ): ProfileImageMetadataWithUrl {
        return transaction {
            // 1. Find bucket-key from profile-image-metadata by userId and userType

            val profileImageMetaDataEntity =
                profileImageMetaRepository.findProfileImageMetaDataByUserInfo(userId, userType)
                    ?: throw NotFoundProfileImageMetaDataException(
                        userType = userType,
                        userId = userId,
                        logics = "profileImageService: getProfileImage"
                    )

            // 2. Make S3 presigned URL request using the key
            val getObjectRequest = GetObjectRequest.builder()
                .bucket(profileImageMetaDataEntity.bucketName)
                .key(profileImageMetaDataEntity.s3Key)
                .build()

            val presignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(15)) // URL expires in 15 minutes
                .getObjectRequest(getObjectRequest)
                .build()

            val presignedUrl = s3Presigner.presignGetObject(presignRequest).url().toString()

            // 3. Send to client presigned URL (making new DTO result -> response)
            ProfileImageMetadataWithUrl.of(
                presignedUrl = presignedUrl,
                bucketName = profileImageMetaDataEntity.bucketName,
                s3Key = profileImageMetaDataEntity.s3Key,
                contentType = profileImageMetaDataEntity.contentType,
                size = profileImageMetaDataEntity.size,
                originalFileName = profileImageMetaDataEntity.originalFileName
            )
        }
    }

    fun updateProfileImage(id: Long, body: String): String {
        return "Update profile image with id: $id from service"
    }

    fun deleteProfileImage(id: Long): String {
        return "Delete profile image with id: $id from service"
    }
}
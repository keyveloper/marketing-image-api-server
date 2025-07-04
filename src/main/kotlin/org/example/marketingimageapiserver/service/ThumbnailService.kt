package org.example.marketingimageapiserver.service

import io.github.oshai.kotlinlogging.KotlinLogging
import net.coobird.thumbnailator.Thumbnails
import org.example.marketingimageapiserver.dto.AdThumbnailMetadata
import org.example.marketingimageapiserver.exception.NotFoundAdImageMetaDataException
import org.example.marketingimageapiserver.exception.S3UploadException
import org.example.marketingimageapiserver.repository.AdvertisementImageMetaRepository
import org.example.marketingimageapiserver.repository.ThumbnailMetaRepository
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Service
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.GetObjectRequest
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import software.amazon.awssdk.services.s3.model.S3Exception
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.util.*

@Service
class ThumbnailService(
    private val advertisementImageMetaRepository: AdvertisementImageMetaRepository,
    private val thumbnailMetaRepository: ThumbnailMetaRepository,
    private val s3Client: S3Client,
) {
    private val logger = KotlinLogging.logger {}

    fun makeThumbnail(imageMetaId: Long): ThumbnailResult {
        return transaction {
            // 1. Find entity by imageMetaId
            val imageMetadataEntity = advertisementImageMetaRepository.findByImageMetaId(imageMetaId)
                ?: throw NotFoundAdImageMetaDataException(
                    metaId = imageMetaId,
                    advertisementId = null,
                    logics = "ThumbnailService.makeThumbnail",
                )

            // 2. Update isThumbnail to true
            imageMetadataEntity.isThumbnail = true
            logger.info { "Updated isThumbnail to true for imageMetaId: $imageMetaId" }

            // 3. Download original image from S3
            val originalBucketName = imageMetadataEntity.bucketName
            val originalS3Key = imageMetadataEntity.s3Key

            val getObjectRequest = GetObjectRequest.builder()
                .bucket(originalBucketName)
                .key(originalS3Key)
                .build()

            val originalImageBytes = s3Client.getObject(getObjectRequest).readBytes()
            logger.info { "Downloaded original image from S3: $originalS3Key" }

            // 4. Create thumbnail image (300x300)
            val thumbnailBytes = ByteArrayOutputStream().use { outputStream ->
                Thumbnails.of(ByteArrayInputStream(originalImageBytes))
                    .size(300, 300)
                    .outputFormat("jpg")
                    .toOutputStream(outputStream)
                outputStream.toByteArray()
            }
            logger.info { "Created thumbnail image (300x300) from original image" }

            // 5. Upload thumbnail to S3 marketing-thumbnail-bucket
            val thumbnailBucketName = "marketing-thumbnail-bucket"
            val thumbnailS3Key = "advertisement-thumbnails/${UUID.randomUUID()}-${UUID.randomUUID()}.jpg"

            try {
                val putObjectRequest = PutObjectRequest.builder()
                    .bucket(thumbnailBucketName)
                    .key(thumbnailS3Key)
                    .contentType("image/jpeg")
                    .contentLength(thumbnailBytes.size.toLong())
                    .build()

                val response = s3Client.putObject(
                    putObjectRequest,
                    RequestBody.fromBytes(thumbnailBytes)
                )

                logger.info { "Uploaded thumbnail to S3: $thumbnailS3Key, response: $response" }

                // 6. Save thumbnail metadata to DB
                val thumbnailMetaId = thumbnailMetaRepository.saveThumbnailMetadata(
                    AdThumbnailMetadata.of(
                        advertisementId = imageMetadataEntity.advertisementId,
                        advertisementImageMetaId = imageMetaId,
                        s3Key = thumbnailS3Key,
                        bucketName = thumbnailBucketName,
                        size = thumbnailBytes.size.toLong(),
                        contentType = "image/jpeg"
                    )
                )
                logger.info { "Saved thumbnail metadata to DB with id: $thumbnailMetaId" }

                ThumbnailResult(
                    imageMetaId = imageMetaId,
                    thumbnailMetaId = thumbnailMetaId,
                    originalS3Key = originalS3Key,
                    thumbnailS3Key = thumbnailS3Key,
                    thumbnailBucketName = thumbnailBucketName,
                    thumbnailSize = thumbnailBytes.size.toLong()
                )
            } catch (e: S3Exception) {
                throw S3UploadException(
                    logics = "ThumbnailService.makeThumbnail",
                    message = e.message ?: "Failed to upload thumbnail to S3"
                )
            }
        }
    }
}

data class ThumbnailResult(
    val imageMetaId: Long,
    val thumbnailMetaId: Long,
    val originalS3Key: String,
    val thumbnailS3Key: String,
    val thumbnailBucketName: String,
    val thumbnailSize: Long
)
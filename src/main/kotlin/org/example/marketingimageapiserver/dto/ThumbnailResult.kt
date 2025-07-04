package org.example.marketingimageapiserver.dto

data class ThumbnailResult(
    val imageMetaId: Long,
    val thumbnailMetaId: Long,
    val originalS3Key: String,
    val thumbnailS3Key: String,
    val thumbnailBucketName: String,
    val thumbnailSize: Long
) {
    companion object {
        fun of(
            imageMetaId: Long,
            thumbnailMetaId: Long,
            originalS3Key: String,
            thumbnailS3Key: String,
            thumbnailBucketName: String,
            thumbnailSize: Long
        ): ThumbnailResult {
            return ThumbnailResult(
                imageMetaId = imageMetaId,
                thumbnailMetaId = thumbnailMetaId,
                originalS3Key = originalS3Key,
                thumbnailS3Key = thumbnailS3Key,
                thumbnailBucketName = thumbnailBucketName,
                thumbnailSize = thumbnailSize
            )
        }
    }
}

package org.example.marketingimageapiserver.dto

data class AdThumbnailMetadata(
    val advertisementId: Long,
    val advertisementImageMetaId: Long,
    val s3Key: String,
    val bucketName: String,
    val size: Long,
    val contentType: String
) {
    companion object {
        fun of(
            advertisementId: Long,
            advertisementImageMetaId: Long,
            s3Key: String,
            bucketName: String,
            size: Long,
            contentType: String
        ): AdThumbnailMetadata {
            return AdThumbnailMetadata(
                advertisementId = advertisementId,
                advertisementImageMetaId = advertisementImageMetaId,
                s3Key = s3Key,
                bucketName = bucketName,
                size = size,
                contentType = contentType
            )
        }
    }
}
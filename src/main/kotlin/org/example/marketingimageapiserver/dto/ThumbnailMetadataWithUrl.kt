package org.example.marketingimageapiserver.dto

data class ThumbnailMetadataWithUrl(
    val advertisementId: Long,
    val advertisementImageMetaId: Long,
    val presignedUrl: String,
    val bucketName: String,
    val s3Key: String,
    val contentType: String,
    val size: Long
) {
    companion object {
        fun of(
            advertisementId: Long,
            advertisementImageMetaId: Long,
            presignedUrl: String,
            bucketName: String,
            s3Key: String,
            contentType: String,
            size: Long
        ): ThumbnailMetadataWithUrl {
            return ThumbnailMetadataWithUrl(
                advertisementId = advertisementId,
                advertisementImageMetaId = advertisementImageMetaId,
                presignedUrl = presignedUrl,
                bucketName = bucketName,
                s3Key = s3Key,
                contentType = contentType,
                size = size
            )
        }
    }
}

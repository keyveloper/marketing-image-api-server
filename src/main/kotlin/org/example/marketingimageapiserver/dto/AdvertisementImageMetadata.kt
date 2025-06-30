package org.example.marketingimageapiserver.dto

data class AdvertisementImageMetadata(
    val advertisementId: Long,
    val advertisementDraftId: Long,
    val writerId: String,
    val isThumbnail: Boolean,
    val originalFileName: String?,
    val contentType: String?,
    val size: Long?,
    val bucketName: String,
    val s3Key: String
) {
    companion object {
        fun of(
            advertisementId: Long,
            advertisementDraftId: Long,
            writerId: String,
            isThumbnail: Boolean,
            originalFileName: String?,
            contentType: String,
            size: Long,
            bucketName: String,
            s3Key: String
        ): AdvertisementImageMetadata {
            return AdvertisementImageMetadata(
                advertisementId = advertisementId,
                advertisementDraftId = advertisementDraftId,
                writerId = writerId,
                isThumbnail = isThumbnail,
                originalFileName = originalFileName,
                contentType = contentType,
                size = size,
                bucketName = bucketName,
                s3Key = s3Key
            )
        }
    }
}
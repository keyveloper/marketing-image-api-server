package org.example.marketingimageapiserver.dto

import org.example.marketingimageapiserver.enums.ProfileImageType

data class AdvertiserProfileImageMetadata(
    val userId: String,
    val advertiserProfileDraftId: Long?,
    val imageType: ProfileImageType,
    val originalFileName: String?,
    val contentType: String?,
    val size: Long?,
    val bucketName: String,
    val s3Key: String
) {
    companion object {
        fun of(
            userId: String,
            advertiserProfileDraftId: Long?,
            imageType: ProfileImageType,
            originalFileName: String?,
            contentType: String,
            size: Long,
            bucketName: String,
            s3Key: String
        ): AdvertiserProfileImageMetadata {
            return AdvertiserProfileImageMetadata(
                userId = userId,
                advertiserProfileDraftId = advertiserProfileDraftId,
                imageType = imageType,
                originalFileName = originalFileName,
                contentType = contentType,
                size = size,
                bucketName = bucketName,
                s3Key = s3Key
            )
        }
    }
}

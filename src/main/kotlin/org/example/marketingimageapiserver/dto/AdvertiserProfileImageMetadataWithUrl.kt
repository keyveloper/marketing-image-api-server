package org.example.marketingimageapiserver.dto

import org.example.marketingimageapiserver.enums.ProfileImageType

data class AdvertiserProfileImageMetadataWithUrl(
    val userId: String,
    val userType: String,
    val imageType: ProfileImageType,
    val presignedUrl: String?,
    val bucketName: String?,
    val s3Key: String?,
    val contentType: String?,
    val size: Long?,
    val originalFileName: String?
) {
    companion object {
        fun of(
            userId: String,
            userType: String,
            imageType: ProfileImageType,
            presignedUrl: String,
            bucketName: String,
            s3Key: String,
            contentType: String,
            size: Long,
            originalFileName: String? = null
        ): AdvertiserProfileImageMetadataWithUrl {
            return AdvertiserProfileImageMetadataWithUrl(
                userId = userId,
                userType = userType,
                imageType = imageType,
                presignedUrl = presignedUrl,
                bucketName = bucketName,
                s3Key = s3Key,
                contentType = contentType,
                size = size,
                originalFileName = originalFileName
            )
        }
    }
}

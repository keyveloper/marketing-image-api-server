package org.example.marketingimageapiserver.dto

import org.example.marketingimageapiserver.enums.ProfileImageType
import org.example.marketingimageapiserver.enums.UserType

data class AdvertiserProfileImageMetadata(
    val userId: String,
    val userType: UserType,
    val profileImageType: ProfileImageType,
    val advertiserProfileDraftId: String,
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
            userType: UserType,
            profileImageType: ProfileImageType,
            advertiserProfileDraftId: String,
            imageType: ProfileImageType,
            originalFileName: String?,
            contentType: String,
            size: Long,
            bucketName: String,
            s3Key: String
        ): AdvertiserProfileImageMetadata {
            return AdvertiserProfileImageMetadata(
                userId = userId,
                userType = userType,
                profileImageType = profileImageType,
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

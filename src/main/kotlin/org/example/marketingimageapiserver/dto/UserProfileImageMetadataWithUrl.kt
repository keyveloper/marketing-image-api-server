package org.example.marketingimageapiserver.dto

import org.example.marketingimageapiserver.enums.ProfileImageType
import org.example.marketingimageapiserver.enums.UserType
import java.util.UUID


data class UserProfileImageMetadataWithUrl(
    val userId: UUID,
    val userType: UserType,
    val profileImageType: ProfileImageType,
    val presignedUrl: String?,
    val bucketName: String?,
    val s3Key: String?,
    val contentType: String?,
    val size: Long?,
    val originalFileName: String?
) {
    companion object {
        fun of(
            userId: UUID,
            userType: UserType,
            profileImageType: ProfileImageType,
            presignedUrl: String,
            bucketName: String,
            s3Key: String,
            contentType: String,
            size: Long,
            originalFileName: String? = null
        ): UserProfileImageMetadataWithUrl {
            return UserProfileImageMetadataWithUrl(
                userId = userId,
                userType = userType,
                profileImageType = profileImageType,
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

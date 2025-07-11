package org.example.marketingimageapiserver.dto

import org.example.marketingimageapiserver.enums.ProfileImageType
import org.example.marketingimageapiserver.enums.UserType
import java.util.UUID

data class UserProfileImageMetadata(
    val userId: UUID,
    val userType: UserType,
    val profileImageType: ProfileImageType,
    val profileDraftId: UUID,
    val imageType: ProfileImageType,
    val originalFileName: String?,
    val contentType: String?,
    val size: Long?,
    val bucketName: String,
    val s3Key: String
) {
    companion object {
        fun of(
            userId: UUID,
            userType: UserType,
            profileImageType: ProfileImageType,
            profileDraftId: UUID,
            imageType: ProfileImageType,
            originalFileName: String?,
            contentType: String,
            size: Long,
            bucketName: String,
            s3Key: String
        ): UserProfileImageMetadata {
            return UserProfileImageMetadata(
                userId = userId,
                userType = userType,
                profileImageType = profileImageType,
                profileDraftId = profileDraftId,
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

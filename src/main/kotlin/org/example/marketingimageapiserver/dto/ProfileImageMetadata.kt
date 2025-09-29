package org.example.marketingimageapiserver.dto

import org.example.marketingimageapiserver.enums.ProfileImageType
import org.example.marketingimageapiserver.enums.UserType

data class ProfileImageMetadata(
    val userType: UserType,
    val userId: Long,
    val profileImageType: ProfileImageType,
    val originalFileName: String?,
    val contentType: String?,
    val size: Long?,
    val bucketName: String, // 🎯 저장 bucket이름/경로
    val s3Key: String // 🎯 저장 고유 식별자
) {
    companion object {
        fun of(
            userType: UserType,
            userId: Long,
            profileImageType: ProfileImageType,
            originalFileName: String?,
            contentType: String,
            size: Long,
            bucketName: String,
            s3Key: String
        ): ProfileImageMetadata {
            return ProfileImageMetadata(
                userType = userType,
                userId = userId,
                profileImageType = profileImageType,
                originalFileName = originalFileName,
                contentType = contentType,
                size = size,
                bucketName = bucketName,
                s3Key = s3Key
            )
        }
    }
}
package org.example.marketingimageapiserver.dto

import org.example.marketingimageapiserver.enums.ProfileImageType

data class InfluencerProfileImageMetadata(
    val userId: String,
    val profileInfluencerDraftId: Long?,
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
            profileInfluencerDraftId: Long?,
            imageType: ProfileImageType,
            originalFileName: String?,
            contentType: String,
            size: Long,
            bucketName: String,
            s3Key: String
        ): InfluencerProfileImageMetadata {
            return InfluencerProfileImageMetadata(
                userId = userId,
                profileInfluencerDraftId = profileInfluencerDraftId,
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

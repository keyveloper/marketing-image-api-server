package org.example.marketingimageapiserver.dto

class SaveInfluencerProfileImageResult(
    val id: Long,
    val userId: String,
    val s3Key: String,
    val bucketName: String,
    val contentType: String,
    val size: Long,
    val originalFileName: String?
) {
    companion object {
        fun of(
            id: Long,
            userType: String,
            s3Key: String,
            bucketName: String,
            contentType: String,
            size: Long,
            originalFileName: String?
        ): SaveInfluencerProfileImageResult {
            return SaveInfluencerProfileImageResult(
                id,
                userType,
                s3Key,
                bucketName,
                contentType,
                size,
                originalFileName
            )
        }
    }
}

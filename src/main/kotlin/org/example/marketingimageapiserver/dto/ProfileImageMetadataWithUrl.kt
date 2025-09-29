package org.example.marketingimageapiserver.dto

data class ProfileImageMetadataWithUrl(
    val presignedUrl: String?,
    val bucketName: String?,
    val s3Key: String?,
    val contentType: String?,
    val size: Long?,
    val originalFileName: String?
) {
    companion object {
        fun of(
            presignedUrl: String,
            bucketName: String,
            s3Key: String,
            contentType: String,
            size: Long,
            originalFileName: String? = null
        ): ProfileImageMetadataWithUrl {
            return ProfileImageMetadataWithUrl(
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

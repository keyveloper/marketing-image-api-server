package org.example.marketingimageapiserver.dto

data class DeleteAdImageResult(
    val imageMetaId: Long,
    val size: Long,
    val contentType: String,
    val s3BucketKey: String
) {
    companion object {
        fun of(
            imageMetaId: Long,
            size: Long,
            contentType: String,
            s3BucketKey: String
        ): DeleteAdImageResult {
            return DeleteAdImageResult(
                imageMetaId = imageMetaId,
                size = size,
                contentType = contentType,
                s3BucketKey = s3BucketKey
            )
        }
    }
}
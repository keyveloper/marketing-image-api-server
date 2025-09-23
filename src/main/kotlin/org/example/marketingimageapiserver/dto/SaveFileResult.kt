package org.example.marketingimageapiserver.dto

data class SaveFileResult(
    val id: Long,
    val s3Key: String,
    val bucketName: String,
    val contentType: String,
    val size: Long,
    val originalFileName: String
)
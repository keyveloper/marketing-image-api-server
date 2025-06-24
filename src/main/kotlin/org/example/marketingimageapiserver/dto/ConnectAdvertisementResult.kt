package org.example.marketingimageapiserver.dto

data class ConnectAdvertisementResult(
    val updatedRow: Int,
    val connectedS3BucketKeys: List<String>
)

package org.example.marketingimageapiserver.dto

data class MakeNewAdvertisementImageRequest(
    val userId: Long,
    val isThumbnail: Boolean
)
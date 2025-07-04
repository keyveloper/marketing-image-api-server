package org.example.marketingimageapiserver.dto

data class UploadAdvertisementImageApiRequest(
    val advertisementDraftId: Long,
    val writerId: String,
    val isThumbnail: Boolean,
)
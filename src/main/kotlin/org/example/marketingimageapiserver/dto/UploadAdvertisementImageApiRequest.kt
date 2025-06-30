package org.example.marketingimageapiserver.dto

data class UploadAdvertisementImageApiRequest(
    val writerId: String,
    val isThumbnail: Boolean,
    val advertisementDraftId: Long,
)
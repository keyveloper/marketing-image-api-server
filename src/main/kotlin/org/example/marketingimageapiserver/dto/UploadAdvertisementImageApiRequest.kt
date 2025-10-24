package org.example.marketingimageapiserver.dto

data class UploadAdvertisementImageApiRequest(
    val writerId: Long,
    val isThumbnail: Boolean,
    val advertisementDraftId: Long,
)
package org.example.marketingimageapiserver.dto

import java.util.UUID

data class UploadAdvertisementImageApiRequest(
    val writerId: UUID,
    val advertisementDraftId: UUID,
    val isThumbnail: Boolean,
)
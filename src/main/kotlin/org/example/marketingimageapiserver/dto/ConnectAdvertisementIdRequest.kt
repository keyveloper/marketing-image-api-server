package org.example.marketingimageapiserver.dto

import java.util.UUID

data class ConnectAdvertisementIdRequest(
    val draftId: UUID,
    val advertisementId: Long
)
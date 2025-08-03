package org.example.marketingimageapiserver.dto

import java.util.UUID

data class GetUserProfileImagesApiRequest(
    val userIds: List<UUID>
)

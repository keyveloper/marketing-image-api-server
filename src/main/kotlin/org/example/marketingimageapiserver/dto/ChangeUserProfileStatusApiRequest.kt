package org.example.marketingimageapiserver.dto

import org.example.marketingimageapiserver.enums.UserType
import java.util.UUID

data class ChangeUserProfileStatusApiRequest(
    val entityId: Long,
    val userId: UUID,
    val userType: UserType
)

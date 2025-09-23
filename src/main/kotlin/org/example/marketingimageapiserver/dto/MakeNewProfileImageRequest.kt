package org.example.marketingimageapiserver.dto

import org.example.marketingimageapiserver.enums.ProfileImageType
import java.time.LocalDateTime

data class MakeNewProfileImageRequest(
    val userType: String,
    val userId: Long,
    val profileImageType: ProfileImageType, // background, profile
)
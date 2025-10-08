package org.example.marketingimageapiserver.dto

import org.example.marketingimageapiserver.enums.ProfileImageType
import org.example.marketingimageapiserver.enums.UserType

data class UploadUserProfileImageApiRequest(
    val userType: UserType,
    val userId: Long,
    val profileImageType: ProfileImageType, // background, profile
)
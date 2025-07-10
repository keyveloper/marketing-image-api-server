package org.example.marketingimageapiserver.dto

import org.example.marketingimageapiserver.enums.ProfileImageType
import org.example.marketingimageapiserver.enums.UserType

data class UploadAdvertiserProfileImageApiRequest(
    val userId: String,
    val userType: UserType,
    val advertiserProfileDraftId: String,
    val profileImageType: ProfileImageType,
)

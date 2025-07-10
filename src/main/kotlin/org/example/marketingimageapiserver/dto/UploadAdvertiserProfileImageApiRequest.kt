package org.example.marketingimageapiserver.dto

import org.example.marketingimageapiserver.enums.ProfileImageType

data class UploadAdvertiserProfileImageApiRequest(
    val userId: String,
    val userType: String,
    val advertiserProfileDraftId: Long?,
    val profileImageType: ProfileImageType,
)

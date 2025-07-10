package org.example.marketingimageapiserver.dto

import org.example.marketingimageapiserver.enums.ProfileImageType

data class UploadInfluencerProfileImageApiRequest(
    val userId: String,
    val userType: String,
    val influencerProfileDraftId: Long?,
    val profileImageType: ProfileImageType,
)

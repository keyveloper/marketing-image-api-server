package org.example.marketingimageapiserver.dto

import org.example.marketingimageapiserver.enums.ProfileImageType
import org.example.marketingimageapiserver.enums.UserType
import java.util.UUID

data class UploadUserProfileImageApiRequest(
    val userId: UUID,
    val userType: UserType,
    val profileImageType: ProfileImageType,
    val userProfileDraftId: UUID
)
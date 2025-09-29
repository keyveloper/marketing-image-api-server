package org.example.marketingimageapiserver.dto

data class ProfileImageResponse(
    val profileImageMetadataWithUrl: ProfileImageMetadataWithUrl
) {
    companion object {
        fun of(
            profileImageMetadataWithUrl: ProfileImageMetadataWithUrl,
        ): ProfileImageResponse {
            return ProfileImageResponse(
                profileImageMetadataWithUrl
            )
        }
    }
}
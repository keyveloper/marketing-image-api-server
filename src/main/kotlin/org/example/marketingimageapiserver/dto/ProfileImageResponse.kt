package org.example.marketingimageapiserver.dto

data class ProfileImageResponse(
    val profileImageMetadataWithUrl: List<ProfileImageMetadataWithUrl>
) {
    companion object {
        fun of(
            profileImageMetadataWithUrl: List<ProfileImageMetadataWithUrl>,
        ): ProfileImageResponse {
            return ProfileImageResponse(
                profileImageMetadataWithUrl
            )
        }
    }
}
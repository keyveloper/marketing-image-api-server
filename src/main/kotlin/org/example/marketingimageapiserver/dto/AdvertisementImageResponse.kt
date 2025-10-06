package org.example.marketingimageapiserver.dto

class AdvertisementImageResponse(
    val advertisementImageMetadataWithUrl: List<AdvertisementImageMetadataWithUrl>
) {
    companion object {
        fun of(
            advertisementImageMetadataWithUrl: List<AdvertisementImageMetadataWithUrl>
        ): AdvertisementImageResponse {
            return AdvertisementImageResponse(advertisementImageMetadataWithUrl)
        }
    }
}
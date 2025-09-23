package org.example.marketingimageapiserver.service

import org.springframework.stereotype.Service

@Service
class AdvertisementImageService {

    fun getAllAdvertisementImages(): String {
        return "Get all advertisement images from service"
    }

    fun getAdvertisementImageById(id: Long): String {
        return "Get advertisement image with id: $id from service"
    }

    fun createAdvertisementImage(body: String): String {
        return "Create advertisement image from service"
    }

    fun updateAdvertisementImage(id: Long, body: String): String {
        return "Update advertisement image with id: $id from service"
    }

    fun deleteAdvertisementImage(id: Long): String {
        return "Delete advertisement image with id: $id from service"
    }
}
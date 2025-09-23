package org.example.marketingimageapiserver.service

import org.springframework.stereotype.Service

@Service
class AdvertisementImageMetaService {

    fun getAllAdvertisementImageMeta(): String {
        return "Get all advertisement image meta from service"
    }

    fun getAdvertisementImageMetaById(id: Long): String {
        return "Get advertisement image meta with id: $id from service"
    }

    fun createAdvertisementImageMeta(body: String): String {
        return "Create advertisement image meta from service"
    }

    fun updateAdvertisementImageMeta(id: Long, body: String): String {
        return "Update advertisement image meta with id: $id from service"
    }

    fun deleteAdvertisementImageMeta(id: Long): String {
        return "Delete advertisement image meta with id: $id from service"
    }
}
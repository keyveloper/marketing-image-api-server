package org.example.marketingimageapiserver.service

import org.springframework.stereotype.Service

@Service
class ProfileImageMetaService {

    fun getAllProfileImageMeta(): String {
        return "Get all profile image meta from service"
    }

    fun getProfileImageMetaById(id: Long): String {
        return "Get profile image meta with id: $id from service"
    }

    fun createProfileImageMeta(body: String): String {
        return "Create profile image meta from service"
    }

    fun updateProfileImageMeta(id: Long, body: String): String {
        return "Update profile image meta with id: $id from service"
    }

    fun deleteProfileImageMeta(id: Long): String {
        return "Delete profile image meta with id: $id from service"
    }
}
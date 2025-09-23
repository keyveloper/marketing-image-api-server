package org.example.marketingimageapiserver.repository

import org.example.marketingimageapiserver.dto.ProfileImageMetadata
import org.example.marketingimageapiserver.dto.ProfileImageMetadataEntity
import org.springframework.stereotype.Component

@Component
class ProfileImageMetaRepository {

    fun saveProfileImageMetadata(domain: ProfileImageMetadata): Long {
        val entity = ProfileImageMetadataEntity.new {
            // Note: ProfileImageMetadata DTO is missing userType, userId, imageType fields
            // These need to be added to the DTO or passed separately
            this.originalFileName = domain.originalFileName ?: ""
            this.contentType = domain.contentType ?: ""
            this.size = domain.size ?: 0L
            this.bucketName = domain.bucketName
            this.s3Key = domain.s3Key
        }
        return entity.id.value
    }
}
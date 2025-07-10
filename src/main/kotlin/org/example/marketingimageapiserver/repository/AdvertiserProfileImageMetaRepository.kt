package org.example.marketingimageapiserver.repository

import org.example.marketingimageapiserver.dto.AdvertiserProfileImageMetadata
import org.example.marketingimageapiserver.dto.AdvertiserProfileImageMetadataEntity
import org.example.marketingimageapiserver.table.AdvertiserProfileImageMetadataTable
import org.springframework.stereotype.Component

@Component
class AdvertiserProfileImageMetaRepository {

    fun saveAdvertiserProfileImageMetadata(domain: AdvertiserProfileImageMetadata): Long {
        val entity = AdvertiserProfileImageMetadataEntity.new {
            this.userId = domain.userId
            this.advertiserProfileDraftId = domain.advertiserProfileDraftId
            this.imageType = domain.imageType
            this.originalFileName = domain.originalFileName ?: ""
            this.contentType = domain.contentType ?: ""
            this.size = domain.size ?: 0L
            this.bucketName = domain.bucketName
            this.s3Key = domain.s3Key
        }
        return entity.id.value
    }

    fun findAdvertiserProfileImageByUserId(userId: String): List<AdvertiserProfileImageMetadataEntity> {
        return AdvertiserProfileImageMetadataEntity.find {
            AdvertiserProfileImageMetadataTable.userId eq userId
        }.toList()
    }
}

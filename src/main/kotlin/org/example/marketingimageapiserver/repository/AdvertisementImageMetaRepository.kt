package org.example.marketingimageapiserver.repository

import org.example.marketingimageapiserver.dto.AdvertisementImageMetadata
import org.example.marketingimageapiserver.dto.AdvertisementImageMetadataEntity
import org.example.marketingimageapiserver.table.AdvertisementImageMetadataTable
import org.springframework.stereotype.Component

@Component
class AdvertisementImageMetaRepository {

    fun saveAdvertisementImageMetadata(domain: AdvertisementImageMetadata): Long {
        val entity = AdvertisementImageMetadataEntity.new {
            this.advertisementId = domain.advertisementId
            this.writerId = domain.writerId
            this.isThumbnail = domain.isThumbnail
            this.originalFileName = domain.originalFileName ?: ""
            this.contentType = domain.contentType ?: ""
            this.size = domain.size ?: 0L
            this.bucketName = domain.bucketName
            this.s3Key = domain.s3Key
        }
        return entity.id.value
    }

    fun findAdvertisementImageMetaDataByAdvertisementId(advertisementId: Long): List<AdvertisementImageMetadataEntity> {
        return AdvertisementImageMetadataEntity.find {
            AdvertisementImageMetadataTable.advertisementId eq advertisementId
        }.toList()
    }

    fun findByImageMetaId(metaId: Long): AdvertisementImageMetadataEntity? {
        return AdvertisementImageMetadataEntity.findById(metaId)
    }
}
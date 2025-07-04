package org.example.marketingimageapiserver.repository

import org.example.marketingimageapiserver.dto.AdThumbnailMetadata
import org.example.marketingimageapiserver.dto.AdvertisementThumbnailMetadataEntity
import org.example.marketingimageapiserver.table.AdvertisementThumbnailMetadataTable
import org.springframework.stereotype.Component

@Component
class ThumbnailMetaRepository {

    fun saveThumbnailMetadata(domain: AdThumbnailMetadata): Long {
        val entity = AdvertisementThumbnailMetadataEntity.new {
            this.advertisementId = domain.advertisementId
            this.advertisementImageMetaId = domain.advertisementImageMetaId
            this.s3Key = domain.s3Key
            this.bucketName = domain.bucketName
            this.size = domain.size
            this.contentType = domain.contentType
        }
        return entity.id.value
    }

    fun findByAdvertisementId(advertisementId: Long): List<AdvertisementThumbnailMetadataEntity> {
        return AdvertisementThumbnailMetadataEntity.find {
            AdvertisementThumbnailMetadataTable.advertisementId eq advertisementId
        }.toList()
    }

    fun findById(id: Long): AdvertisementThumbnailMetadataEntity? {
        return AdvertisementThumbnailMetadataEntity.findById(id)
    }
}
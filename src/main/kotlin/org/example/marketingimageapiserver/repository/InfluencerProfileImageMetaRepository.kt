package org.example.marketingimageapiserver.repository

import org.example.marketingimageapiserver.dto.InfluencerProfileImageMetadata
import org.example.marketingimageapiserver.dto.InfluencerProfileImageMetadataEntity
import org.example.marketingimageapiserver.table.InfluencerProfileImageMetadataTable
import org.springframework.stereotype.Component

@Component
class InfluencerProfileImageMetaRepository {

    fun saveInfluencerProfileImageMetadata(domain: InfluencerProfileImageMetadata): Long {
        val entity = InfluencerProfileImageMetadataEntity.new {
            this.userId = domain.userId
            this.profileInfluencerDraftId = domain.profileInfluencerDraftId
            this.imageType = domain.imageType
            this.originalFileName = domain.originalFileName ?: ""
            this.contentType = domain.contentType ?: ""
            this.size = domain.size ?: 0L
            this.bucketName = domain.bucketName
            this.s3Key = domain.s3Key
        }
        return entity.id.value
    }

    fun findInfluencerProfileImageByUserId(userId: String): List<InfluencerProfileImageMetadataEntity> {
        return InfluencerProfileImageMetadataEntity.find {
            InfluencerProfileImageMetadataTable.userId eq userId
        }.toList()
    }
}

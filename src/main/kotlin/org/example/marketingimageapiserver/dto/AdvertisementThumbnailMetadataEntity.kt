package org.example.marketingimageapiserver.dto

import org.example.marketingimageapiserver.table.AdvertisementThumbnailMetadataTable
import org.jetbrains.exposed.dao.id.EntityID

class AdvertisementThumbnailMetadataEntity(id: EntityID<Long>): BaseDateEntity(id, AdvertisementThumbnailMetadataTable) {
    companion object: BaseDateEntityAutoUpdate<AdvertisementThumbnailMetadataEntity>(AdvertisementThumbnailMetadataTable)
    var advertisementId: Long by AdvertisementThumbnailMetadataTable.advertisementId
    var advertisementImageMetaId: Long by AdvertisementThumbnailMetadataTable.advertisementImageMetaId
    var s3Key: String by AdvertisementThumbnailMetadataTable.s3Key
    var bucketName: String by AdvertisementThumbnailMetadataTable.bucketName
    var size: Long by AdvertisementThumbnailMetadataTable.size
    var contentType: String by AdvertisementThumbnailMetadataTable.contentType
}
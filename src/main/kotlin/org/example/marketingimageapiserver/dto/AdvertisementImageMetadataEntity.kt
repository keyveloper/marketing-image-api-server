package org.example.marketingimageapiserver.dto

import org.example.marketingimageapiserver.table.AdvertisementImageMetadataTable
import org.jetbrains.exposed.dao.id.EntityID

class AdvertisementImageMetadataEntity(id: EntityID<Long>): BaseDateEntity(id, AdvertisementImageMetadataTable) {
    companion object: BaseDateEntityAutoUpdate<AdvertisementImageMetadataEntity>(AdvertisementImageMetadataTable)
    var advertisementId: Long by AdvertisementImageMetadataTable.advertisementId
    var writerId: Long by AdvertisementImageMetadataTable.writerId
    var isThumbnail: Boolean by AdvertisementImageMetadataTable.isThumbnail
    var originalFileName: String by AdvertisementImageMetadataTable.originalFileName
    var contentType: String by AdvertisementImageMetadataTable.contentType
    var size: Long by AdvertisementImageMetadataTable.size
    var bucketName: String by AdvertisementImageMetadataTable.bucketName
    var s3Key: String by AdvertisementImageMetadataTable.s3Key
}
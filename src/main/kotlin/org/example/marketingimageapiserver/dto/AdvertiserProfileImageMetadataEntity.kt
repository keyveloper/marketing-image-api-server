package org.example.marketingimageapiserver.dto

import org.example.marketingimageapiserver.enums.ProfileImageType
import org.example.marketingimageapiserver.table.AdvertiserProfileImageMetadataTable
import org.jetbrains.exposed.dao.id.EntityID

class AdvertiserProfileImageMetadataEntity(id: EntityID<Long>): BaseDateEntity(id, AdvertiserProfileImageMetadataTable) {
    companion object: BaseDateEntityAutoUpdate<AdvertiserProfileImageMetadataEntity>(AdvertiserProfileImageMetadataTable)
    var userId: String by AdvertiserProfileImageMetadataTable.userId
    var profileAdvertiserDraftId: Long? by AdvertiserProfileImageMetadataTable.profileAdvertiserDraftId
    var imageType: ProfileImageType by AdvertiserProfileImageMetadataTable.imageType
    var originalFileName: String by AdvertiserProfileImageMetadataTable.originalFileName
    var contentType: String by AdvertiserProfileImageMetadataTable.contentType
    var size: Long by AdvertiserProfileImageMetadataTable.size
    var bucketName: String by AdvertiserProfileImageMetadataTable.bucketName
    var s3Key: String by AdvertiserProfileImageMetadataTable.s3Key
}

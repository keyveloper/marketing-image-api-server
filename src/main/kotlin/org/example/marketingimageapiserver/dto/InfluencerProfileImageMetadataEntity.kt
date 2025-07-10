package org.example.marketingimageapiserver.dto

import org.example.marketingimageapiserver.enums.ProfileImageType
import org.example.marketingimageapiserver.table.InfluencerProfileImageMetadataTable
import org.jetbrains.exposed.dao.id.EntityID

class InfluencerProfileImageMetadataEntity(id: EntityID<Long>): BaseDateEntity(id, InfluencerProfileImageMetadataTable) {
    companion object: BaseDateEntityAutoUpdate<InfluencerProfileImageMetadataEntity>(InfluencerProfileImageMetadataTable)
    var userId: String by InfluencerProfileImageMetadataTable.userId
    var profileInfluencerDraftId: Long? by InfluencerProfileImageMetadataTable.profileInfluencerDraftId
    var imageType: ProfileImageType by InfluencerProfileImageMetadataTable.imageType
    var originalFileName: String by InfluencerProfileImageMetadataTable.originalFileName
    var contentType: String by InfluencerProfileImageMetadataTable.contentType
    var size: Long by InfluencerProfileImageMetadataTable.size
    var bucketName: String by InfluencerProfileImageMetadataTable.bucketName
    var s3Key: String by InfluencerProfileImageMetadataTable.s3Key
}

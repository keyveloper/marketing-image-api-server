package org.example.marketingimageapiserver.dto

import org.example.marketingimageapiserver.table.ProfileImageMetadataTable
import org.jetbrains.exposed.dao.id.EntityID

class ProfileImageMetadataEntity(id: EntityID<Long>): BaseDateEntity(id, ProfileImageMetadataTable) {
    companion object: BaseDateEntityAutoUpdate<ProfileImageMetadataEntity>(ProfileImageMetadataTable)

    var userType by ProfileImageMetadataTable.userType
    var userId by ProfileImageMetadataTable.userId
    var imageType by ProfileImageMetadataTable.imageType
    var originalFileName by ProfileImageMetadataTable.originalFileName
    var contentType by ProfileImageMetadataTable.contentType
    var size by ProfileImageMetadataTable.size
    var bucketName by ProfileImageMetadataTable.bucketName
    var s3Key by ProfileImageMetadataTable.s3Key
}
package org.example.marketingimageapiserver.dto

import org.example.marketingimageapiserver.enums.ProfileImageType
import org.example.marketingimageapiserver.enums.UserType
import org.example.marketingimageapiserver.table.ProfileImageMetadataTable
import org.jetbrains.exposed.dao.id.EntityID

class ProfileImageMetadataEntity(id: EntityID<Long>): BaseDateEntity(id, ProfileImageMetadataTable) {
    companion object: BaseDateEntityAutoUpdate<ProfileImageMetadataEntity>(ProfileImageMetadataTable)

    var userType: UserType by ProfileImageMetadataTable.userType
    var userId: Long by ProfileImageMetadataTable.userId
    var imageType: ProfileImageType by ProfileImageMetadataTable.imageType
    var originalFileName: String by ProfileImageMetadataTable.originalFileName
    var contentType: String by ProfileImageMetadataTable.contentType
    var size: Long by ProfileImageMetadataTable.size
    var bucketName: String by ProfileImageMetadataTable.bucketName
    var s3Key: String by ProfileImageMetadataTable.s3Key
}
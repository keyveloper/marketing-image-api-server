package org.example.marketingimageapiserver.dto

import org.example.marketingimageapiserver.enums.ProfileImageType
import org.example.marketingimageapiserver.enums.UserType
import org.example.marketingimageapiserver.table.UserProfileImageMetadataTable
import org.jetbrains.exposed.dao.id.EntityID
import java.util.UUID

class UserProfileImageMetadataEntity(id: EntityID<Long>): BaseDateEntity(id, UserProfileImageMetadataTable) {
    companion object: BaseDateEntityAutoUpdate<UserProfileImageMetadataEntity>(UserProfileImageMetadataTable)
    var userId: UUID by UserProfileImageMetadataTable.userId
    var userType: UserType by UserProfileImageMetadataTable.userType
    var profileImageType: ProfileImageType by UserProfileImageMetadataTable.profileImageType
    var userProfileDraftDraftId: UUID by UserProfileImageMetadataTable.userProfileDraftDraftId
    var originalFileName: String by UserProfileImageMetadataTable.originalFileName
    var contentType: String by UserProfileImageMetadataTable.contentType
    var size: Long by UserProfileImageMetadataTable.size
    var bucketName: String by UserProfileImageMetadataTable.bucketName
    var s3Key: String by UserProfileImageMetadataTable.s3Key
}

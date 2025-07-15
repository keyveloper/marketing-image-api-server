package org.example.marketingimageapiserver.repository

import org.example.marketingimageapiserver.dto.UserProfileImageMetadata
import org.example.marketingimageapiserver.dto.UserProfileImageMetadataEntity
import org.example.marketingimageapiserver.enums.ProfileMetadataStatus
import org.example.marketingimageapiserver.enums.UserType
import org.example.marketingimageapiserver.exception.CannotChangeProfileStatusException
import org.example.marketingimageapiserver.table.UserProfileImageMetadataTable
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.update
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class UserProfileImageMetaRepository {

    fun saveUserProfileImageMetadata(domain: UserProfileImageMetadata): Long {
        val entity = UserProfileImageMetadataEntity.new {
            this.userId = domain.userId
            this.userType = domain.userType
            this.profileImageType = domain.profileImageType
            this.userProfileDraftDraftId = domain.profileDraftId
            this.originalFileName = domain.originalFileName ?: ""
            this.contentType = domain.contentType ?: ""
            this.size = domain.size ?: 0L
            this.bucketName = domain.bucketName
            this.s3Key = domain.s3Key
            this.profileMetadataStatus = ProfileMetadataStatus.DRAFT
        }
        return entity.id.value
    }

    fun changeDraftToSave(targetEntityId: Long, targetUserId: UUID, targetUserType: UserType): Long {
        val updatedRows = UserProfileImageMetadataTable.update({
            (UserProfileImageMetadataTable.id eq targetEntityId) and
                    (UserProfileImageMetadataTable.userId eq targetUserId) and
                    (UserProfileImageMetadataTable.userType eq targetUserType)
        }) {
            it[profileMetadataStatus] = ProfileMetadataStatus.SAVE
        }

        if (updatedRows == 0) {
            throw CannotChangeProfileStatusException(
                logics = "userProfileMetaRepository-changeDraftToSave",
                metaId = targetEntityId,
                userType = targetUserType,
                userId = targetUserId
            )
        }

        return targetEntityId
    }

    fun findAdvertiserProfileImageByUserId(userId: UUID): List<UserProfileImageMetadataEntity> {
        return UserProfileImageMetadataEntity.find {
            UserProfileImageMetadataTable.userId eq userId
        }.toList()
    }
}

package org.example.marketingimageapiserver.repository

import org.example.marketingimageapiserver.dto.UserProfileImageMetadata
import org.example.marketingimageapiserver.dto.UserProfileImageMetadataEntity
import org.example.marketingimageapiserver.table.UserProfileImageMetadataTable
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
        }
        return entity.id.value
    }

    fun findAdvertiserProfileImageByUserId(userId: UUID): List<UserProfileImageMetadataEntity> {
        return UserProfileImageMetadataEntity.find {
            UserProfileImageMetadataTable.userId eq userId
        }.toList()
    }
}

package org.example.marketingimageapiserver.repository

import org.example.marketingimageapiserver.dto.ProfileImageMetadata
import org.example.marketingimageapiserver.dto.ProfileImageMetadataEntity
import org.example.marketingimageapiserver.enums.ProfileImageType
import org.example.marketingimageapiserver.enums.UserType
import org.example.marketingimageapiserver.table.ProfileImageMetadataTable
import org.jetbrains.exposed.sql.and
import org.springframework.stereotype.Component

@Component
class ProfileImageMetaRepository {

    fun saveProfileImageMetadata(domain: ProfileImageMetadata): Long {
        val entity = ProfileImageMetadataEntity.new {
            this.userType = domain.userType
            this.userId = domain.userId
            this.imageType = domain.profileImageType
            this.originalFileName = domain.originalFileName ?: ""
            this.contentType = domain.contentType ?: ""
            this.size = domain.size ?: 0L
            this.bucketName = domain.bucketName
            this.s3Key = domain.s3Key
        }
        return entity.id.value
    }

    fun findProfileImageMetaDataByUserInfo(userId: Long, userType: UserType): ProfileImageMetadataEntity? {
        return ProfileImageMetadataEntity.find {
            (ProfileImageMetadataTable.userId eq userId) and
            (ProfileImageMetadataTable.userType eq userType) and
            (ProfileImageMetadataTable.imageType eq ProfileImageType.PROFILE)
        }.singleOrNull()
    }
}
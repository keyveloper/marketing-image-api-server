package org.example.marketingimageapiserver.table

import org.example.marketingimageapiserver.enums.ProfileImageType
import org.jetbrains.exposed.sql.Column

object AdvertiserProfileImageMetadataTable : BaseDateLongIdTable("advertiser_profile_image_metadata") {
    val userId: Column<String> = varchar("user_id", 255)
    val profileAdvertiserDraftId: Column<Long?> = long("profile_advertiser_draft_id").nullable()
    val imageType: Column<ProfileImageType> = enumerationByName("image_type", 255, ProfileImageType::class)
    val originalFileName: Column<String> = varchar("original_file_name", 255)
    val contentType: Column<String> = varchar("content_type", 100)
    val size: Column<Long> = long("size")
    val bucketName: Column<String> = varchar("bucket_name", 255)
    val s3Key: Column<String> = varchar("s3_key", 500)
}

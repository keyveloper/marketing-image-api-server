package org.example.marketingimageapiserver.table

import org.example.marketingimageapiserver.enums.ProfileImageType
import org.example.marketingimageapiserver.enums.UserType
import org.jetbrains.exposed.sql.Column

object AdvertiserProfileImageMetadataTable : BaseDateLongIdTable("advertiser_profile_image_metadata") {
    val userId: Column<String> = varchar("user_id", 255)
    val userType: Column<UserType> = enumerationByName("user_type", 255, UserType::class)
    val profileImageType: Column<ProfileImageType> =
        enumerationByName("profile_image_type", 255, ProfileImageType::class)
    val advertiserProfileDraftDraftId: Column<String> = varchar("advertiser_profile_draft_id", 255)
    val imageType: Column<ProfileImageType> = enumerationByName("image_type", 255, ProfileImageType::class)
    val originalFileName: Column<String> = varchar("original_file_name", 255)
    val contentType: Column<String> = varchar("content_type", 100)
    val size: Column<Long> = long("size")
    val bucketName: Column<String> = varchar("bucket_name", 255)
    val s3Key: Column<String> = varchar("s3_key", 500)
}

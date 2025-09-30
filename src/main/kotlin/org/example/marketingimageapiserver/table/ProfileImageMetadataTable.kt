package org.example.marketingimageapiserver.table

import org.example.marketingimageapiserver.enums.ProfileImageType
import org.example.marketingimageapiserver.enums.UserType
import org.jetbrains.exposed.sql.Column

object ProfileImageMetadataTable : BaseDateLongIdTable("profile_image_metadata") {
    val userType: Column<UserType> = enumerationByName("user_type", 255,UserType::class)
    val userId: Column<Long> = long("user_id")
    val imageType: Column<ProfileImageType> = enumerationByName("image_type", 255, ProfileImageType::class)
    val originalFileName: Column<String> = varchar("original_file_name", 255)
    val contentType: Column<String> = varchar("content_type", 100)
    val size: Column<Long> = long("size")
    val bucketName: Column<String> = varchar("bucket_name", 255)
    val s3Key: Column<String> = varchar("s3_key", 500)
}
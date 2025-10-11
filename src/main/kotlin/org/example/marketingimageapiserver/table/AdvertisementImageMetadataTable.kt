package org.example.marketingimageapiserver.table

import org.jetbrains.exposed.sql.Column

object AdvertisementImageMetadataTable : BaseDateLongIdTable("ad_image_metadata") {
    val advertisementId: Column<Long> = long("advertisement_id")
    val writerId: Column<Long> = long("writer_id")
    val isThumbnail: Column<Boolean> = bool("is_thumbnail")
    val originalFileName: Column<String> = varchar("original_file_name", 255)
    val contentType: Column<String> = varchar("content_type", 100)
    val size: Column<Long> = long("size")
    val bucketName: Column<String> = varchar("bucket_name", 255)
    val s3Key: Column<String> = varchar("s3_key", 500)
}
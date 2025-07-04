package org.example.marketingimageapiserver.table

import org.jetbrains.exposed.sql.Column

object AdvertisementThumbnailMetadataTable : BaseDateLongIdTable("ad_thumbnail_metadata") {
    val advertisementId: Column<Long> = long("advertisement_id")
    val advertisementImageMetaId: Column<Long> = long("advertisement_image_meta_id")
    val s3Key: Column<String> = varchar("s3_key", 500)
    val bucketName: Column<String> = varchar("bucket_name", 255)
    val size: Column<Long> = long("size")
    val contentType: Column<String> = varchar("content_type", 100)
}
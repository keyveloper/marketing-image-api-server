package org.example.marketingimageapiserver.controller

import org.example.marketingimageapiserver.service.AdvertisementImageService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/advertisement-image-meta")
class AdvertisementImageMetaController(
    private val advertisementImageService: AdvertisementImageService
) {

    @GetMapping
    fun getAllAdvertisementImageMeta(): ResponseEntity<String> {
        return ResponseEntity.ok("Get all advertisement image meta")
    }

    @GetMapping("/{id}")
    fun getAdvertisementImageMetaById(@PathVariable id: Long): ResponseEntity<String> {
        return ResponseEntity.ok("Get advertisement image meta with id: $id")
    }



    @PutMapping("/{id}")
    fun updateAdvertisementImageMeta(@PathVariable id: Long, @RequestBody body: String): ResponseEntity<String> {
        return ResponseEntity.ok("Update advertisement image meta with id: $id")
    }

    @DeleteMapping("/{id}")
    fun deleteAdvertisementImageMeta(@PathVariable id: Long): ResponseEntity<String> {
        return ResponseEntity.ok("Delete advertisement image meta with id: $id")
    }
}
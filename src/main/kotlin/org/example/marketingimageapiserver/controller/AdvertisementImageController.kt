package org.example.marketingimageapiserver.controller

import org.example.marketingimageapiserver.dto.MakeNewAdvertisementImageRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/advertisement-images")
class AdvertisementImageController {

    @GetMapping
    fun getAllAdvertisementImages(): ResponseEntity<String> {
        return ResponseEntity.ok("Get all advertisement images")
    }

    @GetMapping("/{id}")
    fun getAdvertisementImageById(@PathVariable id: Long): ResponseEntity<String> {
        return ResponseEntity.ok("Get advertisement image with id: $id")
    }

    @PostMapping
    fun createAdvertisementImage(
        @RequestPart("meta") meta: MakeNewAdvertisementImageRequest,
        @RequestPart("file") file: MultipartFile
    ): ResponseEntity<String> {
        return ResponseEntity.ok("Create advertisement image")
    }

    @PutMapping("/{id}")
    fun updateAdvertisementImage(@PathVariable id: Long, @RequestBody body: String): ResponseEntity<String> {
        return ResponseEntity.ok("Update advertisement image with id: $id")
    }

    @DeleteMapping("/{id}")
    fun deleteAdvertisementImage(@PathVariable id: Long): ResponseEntity<String> {
        return ResponseEntity.ok("Delete advertisement image with id: $id")
    }
}
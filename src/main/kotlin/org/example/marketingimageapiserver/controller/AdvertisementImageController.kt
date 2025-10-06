package org.example.marketingimageapiserver.controller

import org.example.marketingimageapiserver.dto.MakeNewAdvertisementImageRequest
import org.example.marketingimageapiserver.dto.MakeNewAdvertisementImageResponse
import org.example.marketingimageapiserver.enums.MSAServiceErrorCode
import org.example.marketingimageapiserver.service.AdvertisementImageService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/advertisement-images")
class AdvertisementImageController(
    private val advertisementImageService: AdvertisementImageService,
) {

    @PostMapping
    fun createAdvertisementImageMeta(
        @RequestPart("meta") meta: MakeNewAdvertisementImageRequest,
        @RequestPart("file") file: MultipartFile
    ): ResponseEntity<MakeNewAdvertisementImageResponse> {
        val result = advertisementImageService.saveAdvertisementImage(meta, file)

        return ResponseEntity.ok().body(
            MakeNewAdvertisementImageResponse.of(
                result,
                HttpStatus.OK,
                MSAServiceErrorCode.OK
            )
        )
    }
    @GetMapping
    fun getAllAdvertisementImages(): ResponseEntity<String> {
        return ResponseEntity.ok("Get all advertisement images")
    }

    @GetMapping("/{id}")
    fun getAdvertisementImageById(@PathVariable id: Long): ResponseEntity<String> {
        return ResponseEntity.ok("Get advertisement image with id: $id")
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
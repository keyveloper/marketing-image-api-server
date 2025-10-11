package org.example.marketingimageapiserver.controller

import org.example.marketingimageapiserver.dto.AdvertisementImageResponseFromServer
import org.example.marketingimageapiserver.dto.DeleteAdImageResponse
import org.example.marketingimageapiserver.dto.UploadAdvertisementImageApiRequest
import org.example.marketingimageapiserver.dto.UploadAdvertisementImageResponseFromServer
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

    /***
     * 📌 TODO: WriterId and Advertisement validation add need
     ***/
    @PostMapping
    fun createAdvertisementImageMeta(
        @RequestPart("meta") meta: UploadAdvertisementImageApiRequest,
        @RequestPart("file") file: MultipartFile
    ): ResponseEntity<UploadAdvertisementImageResponseFromServer> {
        val result = advertisementImageService.saveAdvertisementImage(meta, file)

        return ResponseEntity.ok().body(
            UploadAdvertisementImageResponseFromServer.of(
                result,
                HttpStatus.OK,
                MSAServiceErrorCode.OK
            )
        )
    }

    @GetMapping("/{adId}")
    fun getAdvertisementImageByAdId(
        @PathVariable("adId") adId: Long,
    ): ResponseEntity<AdvertisementImageResponseFromServer> {
        val result = advertisementImageService.getAdvertisementImageByAdId(adId)
        return ResponseEntity.ok().body(
            AdvertisementImageResponseFromServer.of(
                result,
                HttpStatus.OK,
                MSAServiceErrorCode.OK,
                null,
                null
            )
        )
    }

    @DeleteMapping("/{adImageMetaId}")
    fun deleteAdvertisementImageByMetaId(
        @PathVariable("adImageMetaId") adImageMetaId: Long
    ): ResponseEntity<DeleteAdImageResponse> {
        val result = advertisementImageService.deleteByMetaId(adImageMetaId)
        return ResponseEntity.ok().body(
            DeleteAdImageResponse.of(
                result,
                HttpStatus.OK,
                MSAServiceErrorCode.OK
            )
        )
    }
}
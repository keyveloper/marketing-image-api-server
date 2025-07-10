package org.example.marketingimageapiserver.controller

import org.example.marketingimageapiserver.dto.GetAdvertiserProfileImageResponseFromServer
import org.example.marketingimageapiserver.dto.UploadAdvertiserProfileImageApiRequest
import org.example.marketingimageapiserver.dto.UploadAdvertiserProfileImageResponseFromServer
import org.example.marketingimageapiserver.enums.MSAServiceErrorCode
import org.example.marketingimageapiserver.service.AdvertiserProfileImageService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/advertiser-profile-images")
class AdvertiserProfileImageController(
    private val advertiserProfileImageService: AdvertiserProfileImageService,
) {

    @PostMapping
    fun uploadAdvertiserProfileImage(
        @RequestPart("meta") meta: UploadAdvertiserProfileImageApiRequest,
        @RequestPart("file") file: MultipartFile
    ): ResponseEntity<UploadAdvertiserProfileImageResponseFromServer> {
        val result = advertiserProfileImageService.saveAdvertiserProfileImage(meta, file)

        return ResponseEntity.ok().body(
            UploadAdvertiserProfileImageResponseFromServer.of(
                result,
                HttpStatus.OK,
                MSAServiceErrorCode.OK
            )
        )
    }

    @GetMapping("/{userId}")
    fun getAdvertiserProfileImageByUserId(
        @PathVariable("userId") userId: String,
        @RequestParam("userType") userType: String
    ): ResponseEntity<GetAdvertiserProfileImageResponseFromServer> {
        val result = advertiserProfileImageService.getAdvertiserProfileImageByUserId(userId, userType)
        return ResponseEntity.ok().body(
            GetAdvertiserProfileImageResponseFromServer.of(
                result,
                HttpStatus.OK,
                MSAServiceErrorCode.OK,
                null,
                null
            )
        )
    }
}

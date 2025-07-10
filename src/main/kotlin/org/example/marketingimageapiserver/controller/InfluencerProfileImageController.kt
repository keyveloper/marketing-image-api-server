package org.example.marketingimageapiserver.controller

import org.example.marketingimageapiserver.dto.GetInfluencerProfileImageResponseFromServer
import org.example.marketingimageapiserver.dto.UploadInfluencerProfileImageApiRequest
import org.example.marketingimageapiserver.dto.UploadInfluencerProfileImageResponseFromServer
import org.example.marketingimageapiserver.enums.MSAServiceErrorCode
import org.example.marketingimageapiserver.service.InfluencerProfileImageService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/influencer-profile-images")
class InfluencerProfileImageController(
    private val influencerProfileImageService: InfluencerProfileImageService,
) {

    @PostMapping
    fun uploadInfluencerProfileImage(
        @RequestPart("meta") meta: UploadInfluencerProfileImageApiRequest,
        @RequestPart("file") file: MultipartFile
    ): ResponseEntity<UploadInfluencerProfileImageResponseFromServer> {
        val result = influencerProfileImageService.saveInfluencerProfileImage(meta, file)

        return ResponseEntity.ok().body(
            UploadInfluencerProfileImageResponseFromServer.of(
                result,
                HttpStatus.OK,
                MSAServiceErrorCode.OK
            )
        )
    }

    @GetMapping("/{userId}")
    fun getInfluencerProfileImageByUserId(
        @PathVariable("userId") userId: String,
        @RequestParam("userType") userType: String
    ): ResponseEntity<GetInfluencerProfileImageResponseFromServer> {
        val result = influencerProfileImageService.getInfluencerProfileImageByUserId(userId, userType)
        return ResponseEntity.ok().body(
            GetInfluencerProfileImageResponseFromServer.of(
                result,
                HttpStatus.OK,
                MSAServiceErrorCode.OK,
                null,
                null
            )
        )
    }
}

package org.example.marketingimageapiserver.controller

import org.example.marketingimageapiserver.dto.UploadUserProfileImageApiRequest
import org.example.marketingimageapiserver.dto.UploadUserProfileImageResponseFromServer
import org.example.marketingimageapiserver.enums.MSAServiceErrorCode
import org.example.marketingimageapiserver.service.UserProfileImageService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/user-profile")
class UserProfileImageController(
    private val userProfileImageService: UserProfileImageService,
) {

    @PostMapping
    fun saveUserProfileImage(
        @RequestPart("meta") meta: UploadUserProfileImageApiRequest,
        @RequestPart("file") file: MultipartFile
    ): ResponseEntity<UploadUserProfileImageResponseFromServer> {
        val result = userProfileImageService.saveUserProfileImageAndMetadata(meta, file)

        return ResponseEntity.ok().body(
            UploadUserProfileImageResponseFromServer.of(
                result,
                HttpStatus.OK,
                MSAServiceErrorCode.OK,
            )
        )
    }
}

package org.example.marketingimageapiserver.controller

import io.github.oshai.kotlinlogging.KotlinLogging
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
    private val logger = KotlinLogging.logger {}

    @PostMapping
    fun saveUserProfileImage(
        @RequestPart("meta") meta: UploadUserProfileImageApiRequest,
        @RequestPart("file") file: MultipartFile
    ): ResponseEntity<UploadUserProfileImageResponseFromServer> {
        val result = userProfileImageService.saveUserProfileImageAndMetadata(meta, file)

        val response = ResponseEntity.ok().body(
            UploadUserProfileImageResponseFromServer.of(
                result,
                HttpStatus.OK,
                MSAServiceErrorCode.OK,
            )
        )

        logger.info { "saveUserProfileImage response for userId=${meta.userId}: ${response.body}" }

        return response
    }
}

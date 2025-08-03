package org.example.marketingimageapiserver.controller

import io.github.oshai.kotlinlogging.KotlinLogging
import org.example.marketingimageapiserver.dto.GetUserProfileImagesApiRequest
import org.example.marketingimageapiserver.dto.GetUserProfileImagesResponseFromServer
import org.example.marketingimageapiserver.dto.UploadUserProfileImageApiRequest
import org.example.marketingimageapiserver.dto.UploadUserProfileImageResponseFromServer
import org.example.marketingimageapiserver.dto.UserProfileImageResponseFromServer
import org.example.marketingimageapiserver.enums.MSAServiceErrorCode
import org.example.marketingimageapiserver.service.UserProfileImageService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

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

    @GetMapping("/{userId}")
    fun getUserProfileImagesByUserId(
        @PathVariable("userId") userId: UUID,
    ): ResponseEntity<UserProfileImageResponseFromServer> {
        val result = userProfileImageService.getUserProfileImagesByUserId(userId)
        return ResponseEntity.ok().body(
            UserProfileImageResponseFromServer.of(
                result,
                HttpStatus.OK,
                MSAServiceErrorCode.OK,
                null,
                null
            )
        )
    }

    @PostMapping("/user-profiles")
    fun getUserProfileImagesByUserIds(
        @RequestBody request: GetUserProfileImagesApiRequest
    ): ResponseEntity<GetUserProfileImagesResponseFromServer> {
        val result = userProfileImageService.getUserProfileImagesByUserIds(request.userIds)

        return ResponseEntity.ok().body(
            GetUserProfileImagesResponseFromServer.of(
                result = result,
                httpStatus = HttpStatus.OK,
                msaServiceErrorCode = MSAServiceErrorCode.OK
            )
        )
    }
}

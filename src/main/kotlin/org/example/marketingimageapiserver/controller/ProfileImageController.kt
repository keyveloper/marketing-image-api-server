package org.example.marketingimageapiserver.controller

import org.example.marketingimageapiserver.dto.DeleteProfileImageResponse
import org.example.marketingimageapiserver.dto.UploadUserProfileImageApiRequest
import org.example.marketingimageapiserver.dto.UploadUserProfileImageResponseFromServer
import org.example.marketingimageapiserver.dto.ProfileImageResponse
import org.example.marketingimageapiserver.enums.MSAServiceErrorCode
import org.example.marketingimageapiserver.enums.UserType
import org.example.marketingimageapiserver.service.ProfileImageService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/profile-images")
class ProfileImageController(
    private val profileImageService: ProfileImageService
) {
    @PostMapping
    fun createProfileImage(
        @RequestPart("meta") meta: UploadUserProfileImageApiRequest,
        @RequestPart("file") file: MultipartFile
    ): ResponseEntity<UploadUserProfileImageResponseFromServer> {
        val result = profileImageService.saveProfileImage(meta, file)

        return ResponseEntity.ok().body(
            UploadUserProfileImageResponseFromServer.of(
                result,
                HttpStatus.OK,
                MSAServiceErrorCode.OK
            )
        )
    }

    @GetMapping("/{userId}/{userType}")
    fun getProfileImage(
        @PathVariable("userId") userId: Long,
        @PathVariable("userType") userType: UserType
    ): ResponseEntity<ProfileImageResponse> {
        val result = profileImageService.getProfileImage(userId, userType)
        return ResponseEntity.ok().body(
            ProfileImageResponse.of(result)
        )
    }

    @DeleteMapping("/{imageMetaId}")
    fun deleteProfileImage(@PathVariable imageMetaId: Long): ResponseEntity<DeleteProfileImageResponse> {
        val result = profileImageService.deleteByImageMetaId(imageMetaId)
        return ResponseEntity.ok().body(
            DeleteProfileImageResponse.of(
                result,
                HttpStatus.OK,
                MSAServiceErrorCode.OK
            )
        )
    }
}
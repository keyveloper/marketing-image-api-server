package org.example.marketingimageapiserver.controller

import org.example.marketingimageapiserver.dto.MakeNewProfileImageRequest
import org.example.marketingimageapiserver.dto.MakeNewProfileImageResponse
import org.example.marketingimageapiserver.dto.ProfileImageResponse
import org.example.marketingimageapiserver.enums.UserType
import org.example.marketingimageapiserver.service.ProfileImageService
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
        @RequestPart("meta") meta: MakeNewProfileImageRequest,
        @RequestPart("file") file: MultipartFile
    ): ResponseEntity<MakeNewProfileImageResponse> {
        val result = profileImageService.saveProfileImage(meta, file)

        return ResponseEntity.ok().body(
            MakeNewProfileImageResponse.of(result)
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


    @PutMapping("/{id}")
    fun updateProfileImage(@PathVariable id: Long, @RequestBody body: String): ResponseEntity<String> {
        return ResponseEntity.ok("Update profile image with id: $id")
    }

    @DeleteMapping("/{id}")
    fun deleteProfileImage(@PathVariable id: Long): ResponseEntity<String> {
        return ResponseEntity.ok("Delete profile image with id: $id")
    }
}
package org.example.marketingimageapiserver.controller

import org.example.marketingimageapiserver.dto.ChangeUserProfileStatusApiRequest
import org.example.marketingimageapiserver.dto.ChangeUserProfileStatusResponseFromServer
import org.example.marketingimageapiserver.enums.MSAServiceErrorCode
import org.example.marketingimageapiserver.service.UserProfileImageService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/profile-image-meta")
class UserProfileImageMetaController(
    private val userProfileImageService: UserProfileImageService
) {

    @PostMapping("/change-status")
    fun changeUserProfileStatus(
        @RequestBody request: ChangeUserProfileStatusApiRequest
    ): ResponseEntity<ChangeUserProfileStatusResponseFromServer> {
        val metadataId = userProfileImageService.changeProfileStatusToSave(
            entityId = request.entityId,
            userId = request.userId,
            userType = request.userType
        )

        val response = ChangeUserProfileStatusResponseFromServer.of(
            effectedRow = metadataId,
            httpStatus = HttpStatus.OK,
            msaServiceErrorCode = MSAServiceErrorCode.OK
        )

        return ResponseEntity.ok(response)
    }
}
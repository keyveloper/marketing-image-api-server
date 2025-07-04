package org.example.marketingimageapiserver.controller

import org.example.marketingimageapiserver.dto.MakeThumbnailResponseFromServer
import org.example.marketingimageapiserver.enums.MSAServiceErrorCode
import org.example.marketingimageapiserver.service.ThumbnailService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/advertisement-meta")
class AdvertisementMetaController(
    private val thumbnailService: ThumbnailService,
) {

    @PostMapping("/thumbnail/{imageMetaId}")
    fun makeThumbnail(
        @PathVariable("imageMetaId") imageMetaId: Long
    ): ResponseEntity<MakeThumbnailResponseFromServer> {
        val result = thumbnailService.makeThumbnail(imageMetaId)

        return ResponseEntity.ok().body(
            MakeThumbnailResponseFromServer.of(
                result = result,
                httpStatus = HttpStatus.OK,
                msaServiceErrorCode = MSAServiceErrorCode.OK
            )
        )
    }
}
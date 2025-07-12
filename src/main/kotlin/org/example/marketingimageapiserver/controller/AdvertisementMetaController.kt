package org.example.marketingimageapiserver.controller

import io.github.oshai.kotlinlogging.KotlinLogging
import org.example.marketingimageapiserver.dto.FetchAdThumbnailUrlsRequest
import org.example.marketingimageapiserver.dto.MakeThumbnailResponseFromServer
import org.example.marketingimageapiserver.dto.ThumbnailResponseFromServer
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
    private val logger = KotlinLogging.logger {}

    @PostMapping("/thumbnail/{imageMetaId}")
    fun makeThumbnail(
        @PathVariable("imageMetaId") imageMetaId: Long
    ): ResponseEntity<MakeThumbnailResponseFromServer> {
        val result = thumbnailService.makeThumbnail(imageMetaId)

        val response = ResponseEntity.ok().body(
            MakeThumbnailResponseFromServer.of(
                result = result,
                httpStatus = HttpStatus.OK,
                msaServiceErrorCode = MSAServiceErrorCode.OK
            )
        )

        logger.info { "makeThumbnail response for imageMetaId=$imageMetaId: ${response.body}" }

        return response
    }

    @PostMapping("/thumbnails")
    fun getThumbnailsByAdvertisementIds(
        @RequestBody request: FetchAdThumbnailUrlsRequest
    ): ResponseEntity<ThumbnailResponseFromServer> {
        val result = thumbnailService.getThumbnailsByAdvertisementIds(request.advertisementIds)

        return ResponseEntity.ok().body(
            ThumbnailResponseFromServer.of(
                result = result,
                httpStatus = HttpStatus.OK,
                msaServiceErrorCode = MSAServiceErrorCode.OK
            )
        )
    }
}
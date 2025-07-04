package org.example.marketingimageapiserver.dto

import org.example.marketingimageapiserver.enums.MSAServiceErrorCode
import org.example.marketingimageapiserver.service.ThumbnailResult
import org.springframework.http.HttpStatus

data class MakeThumbnailResponseFromServer(
    val thumbnailResult: ThumbnailResult?,
    override val httpStatus: HttpStatus,
    override val msaServiceErrorCode: MSAServiceErrorCode,
    override val errorMessage: String? = null,
    override val logics: String? = null
): MSABusinessErrorResponse(httpStatus, msaServiceErrorCode) {
    companion object {
        fun of(
            result: ThumbnailResult,
            httpStatus: HttpStatus,
            msaServiceErrorCode: MSAServiceErrorCode,
        ): MakeThumbnailResponseFromServer {
            return MakeThumbnailResponseFromServer(
                thumbnailResult = result,
                httpStatus = httpStatus,
                msaServiceErrorCode = msaServiceErrorCode,
            )
        }
    }
}
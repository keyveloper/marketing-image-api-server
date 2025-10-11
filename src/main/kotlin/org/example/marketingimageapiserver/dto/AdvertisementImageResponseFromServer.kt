package org.example.marketingimageapiserver.dto

import org.example.marketingimageapiserver.enums.MSAServiceErrorCode
import org.springframework.http.HttpStatus

data class AdvertisementImageResponseFromServer(
    val result: List<AdvertisementImageMetadataWithUrl>,
    override val httpStatus: HttpStatus,
    override val msaServiceErrorCode: MSAServiceErrorCode,
    override val errorMessage: String? = null,
    override val logics: String? = null

): MSABusinessErrorResponse(httpStatus, msaServiceErrorCode, errorMessage, logics) {
    companion object {
        fun of(
            result: List<AdvertisementImageMetadataWithUrl>,
            httpStatus: HttpStatus,
            msaServiceErrorCode: MSAServiceErrorCode,
            errorMessage: String?,
            logics: String?
        ): AdvertisementImageResponseFromServer {
            return AdvertisementImageResponseFromServer(
                result,
                httpStatus,
                msaServiceErrorCode,
                errorMessage,
                logics
            )
        }
    }
}
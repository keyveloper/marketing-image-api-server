package org.example.marketingimageapiserver.dto

import org.example.marketingimageapiserver.enums.MSAServiceErrorCode
import org.springframework.http.HttpStatus

data class GetAdvertiserProfileImageResponseFromServer(
    val result: List<AdvertiserProfileImageMetadataWithUrl>,
    override val httpStatus: HttpStatus,
    override val msaServiceErrorCode: MSAServiceErrorCode,
    override val errorMessage: String? = null,
    override val logics: String? = null
): MSABusinessErrorResponse(httpStatus, msaServiceErrorCode, errorMessage, logics) {
    companion object {
        fun of(
            result: List<AdvertiserProfileImageMetadataWithUrl>,
            httpStatus: HttpStatus,
            msaServiceErrorCode: MSAServiceErrorCode,
            errorMessage: String?,
            logics: String?
        ): GetAdvertiserProfileImageResponseFromServer {
            return GetAdvertiserProfileImageResponseFromServer(
                result,
                httpStatus,
                msaServiceErrorCode,
                errorMessage,
                logics
            )
        }
    }
}

package org.example.marketingimageapiserver.dto

import org.example.marketingimageapiserver.enums.MSAServiceErrorCode
import org.springframework.http.HttpStatus
import kotlin.String

data class ConnectAdvertisementResponseFromServer(
    val result: ConnectAdvertisementResult,
    override val httpStatus: HttpStatus,
    override val msaServiceErrorCode: MSAServiceErrorCode,
    override val errorMessage: String? = null,
    override val logics: String? = null
): MSABusinessErrorResponse(httpStatus, msaServiceErrorCode, errorMessage, logics) {
    companion object {
        fun of(
            result: ConnectAdvertisementResult,
            httpStatus: HttpStatus,
            msaServiceErrorCode: MSAServiceErrorCode,
        ): ConnectAdvertisementResponseFromServer {
            return ConnectAdvertisementResponseFromServer(
                result = result,
                httpStatus = httpStatus,
                msaServiceErrorCode = msaServiceErrorCode,
            )
        }
    }
}
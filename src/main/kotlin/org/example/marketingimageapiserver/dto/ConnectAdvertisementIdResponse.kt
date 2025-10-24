package org.example.marketingimageapiserver.dto

import org.example.marketingimageapiserver.enums.MSAServiceErrorCode
import org.springframework.http.HttpStatus

data class ConnectAdvertisementIdResponse(
    val updatedCount: Int,
    override val httpStatus: HttpStatus,
    override val msaServiceErrorCode: MSAServiceErrorCode,
    override val errorMessage: String? = null,
    override val logics: String? = null
): MSABusinessErrorResponse(httpStatus, msaServiceErrorCode, errorMessage, logics) {
    companion object {
        fun of(
            updatedCount: Int,
            httpStatus: HttpStatus,
            msaServiceErrorCode: MSAServiceErrorCode
        ): ConnectAdvertisementIdResponse {
            return ConnectAdvertisementIdResponse(
                updatedCount = updatedCount,
                httpStatus = httpStatus,
                msaServiceErrorCode = msaServiceErrorCode
            )
        }
    }
}
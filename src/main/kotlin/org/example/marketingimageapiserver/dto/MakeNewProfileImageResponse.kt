package org.example.marketingimageapiserver.dto

import org.example.marketingimageapiserver.enums.MSAServiceErrorCode
import org.springframework.http.HttpStatus

data class MakeNewProfileImageResponse(
    val saveFileResult: SaveFileResult?,
    override val httpStatus: HttpStatus,
    override val msaServiceErrorCode: MSAServiceErrorCode,
    override val errorMessage: String? = null,
    override val logics: String? = null
): MSABusinessErrorResponse(httpStatus, msaServiceErrorCode) {
    companion object {
        fun of(
            result: SaveFileResult,
            httpStatus: HttpStatus,
            msaServiceErrorCode: MSAServiceErrorCode,
        ): MakeNewProfileImageResponse {
            return MakeNewProfileImageResponse(
                saveFileResult = result,
                httpStatus = httpStatus,
                msaServiceErrorCode = msaServiceErrorCode,
            )
        }
    }
}
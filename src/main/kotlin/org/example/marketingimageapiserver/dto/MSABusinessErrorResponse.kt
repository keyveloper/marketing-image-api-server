package org.example.marketingimageapiserver.dto

import org.example.marketingimageapiserver.enums.MSAServiceErrorCode
import org.springframework.http.HttpStatus

open class MSABusinessErrorResponse(
    open val httpStatus: HttpStatus,
    open val msaServiceErrorCode: MSAServiceErrorCode,
    open val errorMessage: String,
    open val logics: String,
) {
    companion object {
        fun of(
            httpStatus: HttpStatus,
            msaServiceErrorCode: MSAServiceErrorCode,
            errorMessage: String,
            logics: String
        ): MSABusinessErrorResponse {
            return MSABusinessErrorResponse(
                httpStatus,
                msaServiceErrorCode,
                errorMessage,
                logics
            )
        }
    }
}
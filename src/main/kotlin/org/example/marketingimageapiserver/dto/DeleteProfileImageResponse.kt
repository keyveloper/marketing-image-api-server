package org.example.marketingimageapiserver.dto

import org.example.marketingimageapiserver.enums.MSAServiceErrorCode
import org.springframework.http.HttpStatus

data class DeleteProfileImageResponse(
    val deleteProfileImageResult: DeleteProfileImageResult,
    override val httpStatus: HttpStatus,
    override val msaServiceErrorCode: MSAServiceErrorCode,
    override val errorMessage: String? = null,
    override val logics: String? = null
): MSABusinessErrorResponse(httpStatus, msaServiceErrorCode, errorMessage, logics) {
    companion object {
        fun of(
            deleteProfileImageResult: DeleteProfileImageResult,
            httpStatus: HttpStatus,
            msaServiceErrorCode: MSAServiceErrorCode
        ): DeleteProfileImageResponse {
            return DeleteProfileImageResponse(
                deleteProfileImageResult = deleteProfileImageResult,
                httpStatus = httpStatus,
                msaServiceErrorCode = msaServiceErrorCode
            )
        }
    }
}

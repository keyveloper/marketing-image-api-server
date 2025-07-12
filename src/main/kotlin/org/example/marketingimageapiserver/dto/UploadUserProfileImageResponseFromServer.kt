package org.example.marketingimageapiserver.dto

import org.example.marketingimageapiserver.enums.MSAServiceErrorCode
import org.springframework.http.HttpStatus

data class UploadUserProfileImageResponseFromServer(
    val saveUserProfileImageResult: SaveUserProfileImageResult?,
    override val httpStatus: HttpStatus,
    override val msaServiceErrorCode: MSAServiceErrorCode,
    override val errorMessage: String? = null,
    override val logics: String? = null
): MSABusinessErrorResponse(httpStatus, msaServiceErrorCode) {
    companion object {
        fun of(
            result: SaveUserProfileImageResult,
            httpStatus: HttpStatus,
            msaServiceErrorCode: MSAServiceErrorCode,
        ): UploadUserProfileImageResponseFromServer {
            return UploadUserProfileImageResponseFromServer(
                saveUserProfileImageResult = result,
                httpStatus = httpStatus,
                msaServiceErrorCode = msaServiceErrorCode,
            )
        }
    }
}
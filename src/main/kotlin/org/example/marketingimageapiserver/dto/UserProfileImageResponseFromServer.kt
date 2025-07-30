package org.example.marketingimageapiserver.dto

import org.example.marketingimageapiserver.enums.MSAServiceErrorCode
import org.springframework.http.HttpStatus

data class UserProfileImageResponseFromServer(
    val result: List<UserProfileImageMetadataWithUrl>,
    override val httpStatus: HttpStatus,
    override val msaServiceErrorCode: MSAServiceErrorCode,
    override val errorMessage: String? = null,
    override val logics: String? = null

): MSABusinessErrorResponse(httpStatus, msaServiceErrorCode, errorMessage, logics) {
    companion object {
        fun of(
            result: List<UserProfileImageMetadataWithUrl>,
            httpStatus: HttpStatus,
            msaServiceErrorCode: MSAServiceErrorCode,
            errorMessage: String?,
            logics: String?
        ): UserProfileImageResponseFromServer {
            return UserProfileImageResponseFromServer(
                result,
                httpStatus,
                msaServiceErrorCode,
                errorMessage,
                logics
            )
        }
    }
}

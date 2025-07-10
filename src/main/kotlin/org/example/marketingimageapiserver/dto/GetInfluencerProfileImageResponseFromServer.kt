package org.example.marketingimageapiserver.dto

import org.example.marketingimageapiserver.enums.MSAServiceErrorCode
import org.springframework.http.HttpStatus

data class GetInfluencerProfileImageResponseFromServer(
    val result: List<InfluencerProfileImageMetadataWithUrl>,
    override val httpStatus: HttpStatus,
    override val msaServiceErrorCode: MSAServiceErrorCode,
    override val errorMessage: String? = null,
    override val logics: String? = null
): MSABusinessErrorResponse(httpStatus, msaServiceErrorCode, errorMessage, logics) {
    companion object {
        fun of(
            result: List<InfluencerProfileImageMetadataWithUrl>,
            httpStatus: HttpStatus,
            msaServiceErrorCode: MSAServiceErrorCode,
            errorMessage: String?,
            logics: String?
        ): GetInfluencerProfileImageResponseFromServer {
            return GetInfluencerProfileImageResponseFromServer(
                result,
                httpStatus,
                msaServiceErrorCode,
                errorMessage,
                logics
            )
        }
    }
}

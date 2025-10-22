package org.example.marketingimageapiserver.exception

import org.example.marketingimageapiserver.enums.MSAServiceErrorCode
import org.example.marketingimageapiserver.enums.UserType
import org.springframework.http.HttpStatus

data class NotFoundProfileImageMetaDataException(
    val metaId: Long?,
    val userType: UserType?,
    val userId: Long?,
    override val httpStatus: HttpStatus = HttpStatus.NOT_FOUND,
    override val msaServiceErrorCode: MSAServiceErrorCode = MSAServiceErrorCode.NOT_FOUND_IMAGE_PROFILE_IMAGE,
    override val logics: String,
    override val message: String = "Can't find user's image meta data: ${userType}: ${userId}",
    ): MSAServerException(httpStatus, msaServiceErrorCode, logics, message)

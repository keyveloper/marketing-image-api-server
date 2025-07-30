package org.example.marketingimageapiserver.exception

import org.example.marketingimageapiserver.enums.MSAServiceErrorCode
import org.springframework.http.HttpStatus

class UserProfileImageCountExceededException(
    override val logics: String,
    override val message: String = "User profile image count exceeded. Maximum allowed is 2.",
) : MSAServerException(
    httpStatus = HttpStatus.BAD_REQUEST,
    msaServiceErrorCode = MSAServiceErrorCode.USER_PROFILE_IMAGE_COUNT_EXCEEDED,
    logics = logics,
    message = message
)

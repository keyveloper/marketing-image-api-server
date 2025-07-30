package org.example.marketingimageapiserver.exception

import org.example.marketingimageapiserver.enums.MSAServiceErrorCode
import org.springframework.http.HttpStatus

class DuplicateProfileImageTypeException(
    override val logics: String,
    override val message: String = "Duplicate profile image type found. Each user can have only one BACKGROUND and one PROFILE image.",
) : MSAServerException(
    httpStatus = HttpStatus.BAD_REQUEST,
    msaServiceErrorCode = MSAServiceErrorCode.DUPLICATE_PROFILE_IMAGE_TYPE,
    logics = logics,
    message = message
)

package org.example.marketingimageapiserver.exception

import org.example.marketingimageapiserver.enums.MSAServiceErrorCode
import org.springframework.http.HttpStatus

data class S3DeleteException(
    override val httpStatus: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR,
    override val msaServiceErrorCode: MSAServiceErrorCode = MSAServiceErrorCode.S3_DELETE_FAILED,
    override val logics: String,
    override val message: String = "S3 file deletion failed",
): MSAServerException(httpStatus, msaServiceErrorCode, logics, message)

package org.example.marketingimageapiserver.exception

import org.example.marketingimageapiserver.enums.MSAServiceErrorCode
import org.springframework.http.HttpStatus

data class SaveImageProfileFailedForDatabaseException(
    override val httpStatus: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR,
    override val msaServiceErrorCode: MSAServiceErrorCode = MSAServiceErrorCode.SAVE_FAILED_FOR_DATABASE,
    override val logics: String,
    override val message: String = "Can't save Image metadata for server Database"
): MSAServerException(httpStatus, msaServiceErrorCode, logics, message)
package org.example.marketingimageapiserver.exception

import org.example.marketingimageapiserver.enums.MSAServiceErrorCode
import org.springframework.http.HttpStatus

data class NotFoundAdImageMetaDataException(
    val metaId: Long?,
    val advertisementId: Long?,
    override val httpStatus: HttpStatus = HttpStatus.NOT_FOUND,
    override val msaServiceErrorCode: MSAServiceErrorCode = MSAServiceErrorCode.NOT_FOUND_AD_IMAGE,
    override val logics: String,
    override val message: String = "Can't find advertisement image meta data: advertisementId: ${advertisementId}, metaId: ${metaId}",
    ): MSAServerException(httpStatus, msaServiceErrorCode, logics, message)
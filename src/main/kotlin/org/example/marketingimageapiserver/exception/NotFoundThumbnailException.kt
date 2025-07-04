package org.example.marketingimageapiserver.exception

import org.example.marketingimageapiserver.enums.MSAServiceErrorCode
import org.springframework.http.HttpStatus

data class NotFoundThumbnailException(
    val advertisementIds: List<Long>?,
    override val httpStatus: HttpStatus = HttpStatus.NOT_FOUND,
    override val msaServiceErrorCode: MSAServiceErrorCode = MSAServiceErrorCode.NOT_FOUND_THUMBNAIL,
    override val logics: String,
    override val message: String = "Can't find thumbnail metadata for advertisementIds: ${advertisementIds}",
): MSAServerException(httpStatus, msaServiceErrorCode, logics, message)

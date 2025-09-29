package org.example.marketingimageapiserver.exception

import org.example.marketingimageapiserver.enums.MSAServiceErrorCode
import org.springframework.http.HttpStatus

open class BusinessException(
    open val httpStatus: HttpStatus,
    open val msaServiceErrorCode: MSAServiceErrorCode,
    open val logics: String,
    override val message: String = "Undefined Business Exception. Please resign this Exception.",
): RuntimeException(message)
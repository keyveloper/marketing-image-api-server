package org.example.marketingimageapiserver.exception

import org.apache.http.HttpStatus
import org.example.marketingimageapiserver.dto.MSABusinessErrorResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.http.HttpStatus as SpringHttpStatus

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException::class)
    fun handleBusinessException(exception: BusinessException): ResponseEntity<MSABusinessErrorResponse> {
        return ResponseEntity.ok().body(
            MSABusinessErrorResponse.of(
                exception.httpStatus,
                exception.msaServiceErrorCode,
                exception.message,
                exception.logics
            )
        )
    }

}
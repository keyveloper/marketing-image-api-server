package org.example.marketingimageapiserver.exception

import org.example.marketingimageapiserver.dto.MSABusinessErrorResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(MSAServerException::class)
    fun handleBusinessException(exception: MSAServerException): ResponseEntity<MSABusinessErrorResponse> {
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
package org.example.marketingimageapiserver.exception

import org.example.marketingimageapiserver.enums.MSAServiceErrorCode
import org.example.marketingimageapiserver.enums.UserType
import org.springframework.http.HttpStatus
import java.util.UUID

data class CannotChangeProfileStatusException(
    val metaId: Long?,
    val userType: UserType?,
    val userId: UUID?,
    override val httpStatus: HttpStatus = HttpStatus.BAD_REQUEST,
    override val msaServiceErrorCode: MSAServiceErrorCode = MSAServiceErrorCode.CANNOT_CHANGE_PROFILE_STATUS,
    override val logics: String,
    override val message: String =
        "Cannot change profile status - entity id or user id or user type does not match. " +
                "MetaId: $metaId, UserId: $userId, UserType: $userType",
): MSAServerException(httpStatus, msaServiceErrorCode, logics, message)

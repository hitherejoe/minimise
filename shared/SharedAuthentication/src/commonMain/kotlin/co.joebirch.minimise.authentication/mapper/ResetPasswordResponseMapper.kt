package co.joebirch.minimise.authentication.mapper

import co.joebirch.firebase_auth_multiplatform.model.FirebasePasswordResetResponse
import co.joebirch.minimise.authentication.model.ResetPasswordResponse

open class ResetPasswordResponseMapper {

    fun mapFromResetPasswordResponse(
        resetPasswordResponse: FirebasePasswordResetResponse
    ) = ResetPasswordResponse(
        resetPasswordResponse.email != null && resetPasswordResponse.code == 200,
        resetPasswordResponse.message,
        resetPasswordResponse.code
    )
}
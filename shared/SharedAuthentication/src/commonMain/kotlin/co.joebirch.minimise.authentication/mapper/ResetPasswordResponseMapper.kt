package co.joebirch.minimise.authentication.mapper

import co.joebirch.minimise.authentication.model.ResetPasswordResponse
import co.joebirch.minimise.authentication.model.ResetPasswordResponseModel

open class ResetPasswordResponseMapper {

    fun mapFromResetPasswordResponse(
        resetPasswordResponse: ResetPasswordResponseModel? = null
    ) = ResetPasswordResponse(
      //  resetPasswordResponse.email != null && resetPasswordResponse.code == 200,
        //resetPasswordResponse.message,
        //resetPasswordResponse.code
    )
}
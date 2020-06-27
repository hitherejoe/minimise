package co.joebirch.minimise.authentication.mapper

import co.joebirch.minimise.LoginQuery
import co.joebirch.minimise.authentication.model.AuthenticationModel

fun LoginQuery.Login.toAuthenticationModel(): AuthenticationModel {
    return this.asAuthData?.let {
        AuthenticationModel(it.token)
    } ?: run {
        AuthenticationModel(message = this.asAuthenticationError?.message)
    }
}



open class AuthenticationResponseMapper {

    fun mapFromAuthenticationResponse(
        response: LoginQuery.AsAuthData? = null
    ) = AuthenticationModel(
       // authenticationResponse.idToken,
       // authenticationResponse.message,
       // authenticationResponse.code
    )
}
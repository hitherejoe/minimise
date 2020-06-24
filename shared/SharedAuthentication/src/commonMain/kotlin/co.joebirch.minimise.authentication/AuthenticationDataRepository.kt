package co.joebirch.minimise.authentication

import co.joebirch.minimise.authentication.mapper.AuthenticationResponseMapper
import co.joebirch.minimise.authentication.mapper.ResetPasswordResponseMapper
import co.joebirch.minimise.authentication.model.AuthenticationModel
import co.joebirch.minimise.authentication.model.ResetPasswordResponse
import co.joebirch.minimise.authentication.remote.AuthenticationRemote

class AuthenticationDataRepository constructor(
    private val authenticationStore: AuthenticationRemote,
    private val authenticationResponseMapper: AuthenticationResponseMapper,
    private val resetPasswordResponseMapper: ResetPasswordResponseMapper
) : AuthenticationRepository {

    override suspend fun signUp(
        apiKey: String,
        emailAddress: String,
        password: String
    ): AuthenticationModel {
        return authenticationResponseMapper.mapFromAuthenticationResponse(
            //authenticationStore.signUp(apiKey, emailAddress, password)
        )
    }

    override suspend fun signIn(
        apiKey: String,
        emailAddress: String,
        password: String
    ): AuthenticationModel {
        return authenticationResponseMapper.mapFromAuthenticationResponse(
            //authenticationStore.signIn(apiKey, emailAddress, password)
        )
    }

    override suspend fun resetPassword(
        apiKey: String,
        emailAddress: String
    ): ResetPasswordResponse {
        return resetPasswordResponseMapper.mapFromResetPasswordResponse(
            //authenticationStore.resetPassword(apiKey, emailAddress)
        )
    }
}
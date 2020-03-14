package co.joebirch.minimise.authentication

import co.joebirch.firebase_auth_multiplatform.AuthenticationStore
import co.joebirch.minimise.authentication.mapper.AuthenticationResponseMapper
import co.joebirch.minimise.authentication.mapper.ResetPasswordResponseMapper
import co.joebirch.minimise.authentication.model.AuthenticationModel
import co.joebirch.minimise.authentication.model.ResetPasswordResponse

class AuthenticationDataRepository constructor(
    private val authenticationStore: AuthenticationStore,
    private val authenticationResponseMapper: AuthenticationResponseMapper,
    private val resetPasswordResponseMapper: ResetPasswordResponseMapper
) : AuthenticationRepository {

    override suspend fun signUp(
        apiKey: String,
        emailAddress: String,
        password: String
    ): AuthenticationModel {
        return authenticationResponseMapper.mapFromAuthenticationResponse(
            authenticationStore.signUp(apiKey, emailAddress, password, true)
        )
    }

    override suspend fun signIn(
        apiKey: String,
        emailAddress: String,
        password: String
    ): AuthenticationModel {
        return authenticationResponseMapper.mapFromAuthenticationResponse(
            authenticationStore.signIn(apiKey, emailAddress, password, true)
        )
    }

    override suspend fun resetPassword(
        apiKey: String,
        emailAddress: String
    ): ResetPasswordResponse {
        return resetPasswordResponseMapper.mapFromResetPasswordResponse(
            authenticationStore.resetPassword(apiKey, emailAddress)
        )
    }
}
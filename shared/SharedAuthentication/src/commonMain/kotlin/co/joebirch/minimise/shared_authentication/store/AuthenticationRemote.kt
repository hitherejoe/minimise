package co.joebirch.minimise.shared_authentication.store

import co.joebirch.minimise.shared_authentication.model.AuthenticationModel

interface AuthenticationRemote {

    suspend fun signUp(
        email: String,
        password: String,
        returnSecureToken: Boolean
    ): AuthenticationModel

    suspend fun signIn(
        email: String,
        password: String,
        returnSecureToken: Boolean
    ): AuthenticationModel
}
package co.joebirch.minimise.shared_authentication

import co.joebirch.minimise.shared_authentication.model.AuthenticationModel

interface AuthenticationRepository {

    suspend fun signUp(emailAddress: String, password: String): AuthenticationModel

    suspend fun signIn(emailAddress: String, password: String): AuthenticationModel

}
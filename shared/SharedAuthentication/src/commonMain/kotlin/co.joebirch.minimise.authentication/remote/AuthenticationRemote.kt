package co.joebirch.minimise.authentication.remote

import co.joebirch.minimise.authentication.model.AuthenticationModel
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.ApolloExperimental

interface AuthenticationRemote {

    suspend fun signUp(
        apiKey: String,
        emailAddress: String,
        password: String
    ): AuthenticationModel

    suspend fun signIn(
        apiKey: String,
        emailAddress: String,
        password: String
    ): AuthenticationModel

    fun resetPassword(
        apiKey: String,
        emailAddress: String
    ): AuthenticationModel
}
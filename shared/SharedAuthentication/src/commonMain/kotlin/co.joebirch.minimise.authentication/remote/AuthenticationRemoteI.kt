package co.joebirch.minimise.authentication.remote

import co.joebirch.minimise.authentication.model.AuthenticationModel
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.ApolloExperimental
import com.apollographql.apollo.network.ApolloHttpNetworkTransport

interface AuthenticationRemoteI {

    fun signUp(
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
    )
}
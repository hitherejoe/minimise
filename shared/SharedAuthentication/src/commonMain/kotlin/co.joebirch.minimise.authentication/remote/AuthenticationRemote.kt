package co.joebirch.minimise.authentication.remote

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.ApolloExperimental
import com.apollographql.apollo.network.ApolloHttpNetworkTransport

@ApolloExperimental
class AuthenticationRemote {

    val apolloClient = ApolloClient(
        networkTransport = ApolloHttpNetworkTransport(
            serverUrl = "http://10.0.2.2/graphql",
            headers = mapOf(
                "Accept" to "application/json",
                "Content-Type" to "application/json"
            )
        )
    )

    fun signUp(
        apiKey: String,
        emailAddress: String,
        password: String
    ) {

    }

    fun signIn(
        apiKey: String,
        emailAddress: String,
        password: String
    ) {

    }

    fun resetPassword(
        apiKey: String,
        emailAddress: String
    ) {

    }
}
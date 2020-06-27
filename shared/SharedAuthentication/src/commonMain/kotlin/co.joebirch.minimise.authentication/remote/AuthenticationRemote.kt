package co.joebirch.minimise.authentication.remote

import co.joebirch.minimise.LoginQuery
import co.joebirch.minimise.authentication.mapper.toAuthenticationModel
import co.joebirch.minimise.authentication.model.AuthenticationModel
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.ApolloExperimental
import com.apollographql.apollo.network.ApolloHttpNetworkTransport
import com.apollographql.apollo.network.HttpExecutionContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.single

@ExperimentalCoroutinesApi
@ApolloExperimental
open class AuthenticationRemote: AuthenticationRemoteI {

    val apolloClient = ApolloClient(
        networkTransport = ApolloHttpNetworkTransport(
            serverUrl = "http://10.0.2.2:4000/graphql\n",
            headers = mapOf(
                "Accept" to "application/json",
                "Content-Type" to "application/json"
            )
        )
    )

    override fun signUp(
        apiKey: String,
        emailAddress: String,
        password: String
    ): AuthenticationModel {

    }

    override suspend fun signIn(
        apiKey: String,
        emailAddress: String,
        password: String
    ): AuthenticationModel {
        val repositoriesQuery = LoginQuery(
            emailAddress = emailAddress,
            password = password
        )
        return apolloClient.query(repositoriesQuery).execute().single().data?.login
            ?.toAuthenticationModel() ?: AuthenticationModel()
    }

    override fun resetPassword(
        apiKey: String,
        emailAddress: String
    ) {

    }
}
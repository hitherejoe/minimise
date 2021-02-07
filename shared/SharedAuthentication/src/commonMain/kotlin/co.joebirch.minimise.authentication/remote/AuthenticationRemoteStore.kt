package co.joebirch.minimise.authentication.remote

import co.joebirch.minimise.BuildKonfig
import co.joebirch.minimise.SignInQuery
import co.joebirch.minimise.SignUpMutation
import co.joebirch.minimise.authentication.mapper.toAuthenticationModel
import co.joebirch.minimise.authentication.model.AuthenticationModel
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.ApolloExperimental
import com.apollographql.apollo.network.http.ApolloHttpNetworkTransport
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.single

@ExperimentalCoroutinesApi
@ApolloExperimental
open class AuthenticationRemoteStore: AuthenticationRemote {

    private val apolloClient = ApolloClient(
        networkTransport = ApolloHttpNetworkTransport(
            serverUrl = BuildKonfig.apiUrl,
            headers = mapOf(
                "Accept" to "application/json",
                "Content-Type" to "application/json"
            )
        )
    )

    override suspend fun signUp(
        apiKey: String,
        emailAddress: String,
        password: String
    ): AuthenticationModel {
        val signUp = SignUpMutation(
            emailAddress = emailAddress,
            password = password
        )
        return apolloClient.mutate(signUp).execute().single().data?.createUser?.toAuthenticationModel()
            ?: AuthenticationModel()
    }

    override suspend fun signIn(
        apiKey: String,
        emailAddress: String,
        password: String
    ): AuthenticationModel {
        val repositoriesQuery = SignInQuery(
            emailAddress = emailAddress,
            password = password
        )
        return apolloClient.query(repositoriesQuery).execute().single().data?.login
            ?.toAuthenticationModel() ?: AuthenticationModel()
    }
}
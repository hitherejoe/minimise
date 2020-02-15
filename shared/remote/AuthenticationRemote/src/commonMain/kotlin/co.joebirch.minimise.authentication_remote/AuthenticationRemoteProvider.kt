package co.joebirch.minimise.authentication_remote

import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer

object AuthenticationRemoteProvider {
    fun getAuthenticationRemote(): AuthenticationRemote =
        AuthenticationApi("remote", HttpClient {
            install(JsonFeature) {
                serializer = KotlinxSerializer()
            }
        })
}
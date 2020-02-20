package co.joebirch.minimise.remote

import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer

object HttpClientProvider {
    fun getHttpClient() = HttpClient {
        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }
    }
}
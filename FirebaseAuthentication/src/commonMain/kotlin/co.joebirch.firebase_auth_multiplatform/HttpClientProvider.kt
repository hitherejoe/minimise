package co.joebirch.firebase_auth_multiplatform

import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration

class HttpClientProvider {
    val httpClient = HttpClient {
        install(JsonFeature) {
            serializer = KotlinxSerializer(Json(JsonConfiguration(isLenient = true, ignoreUnknownKeys = true)))
        }
    }
}
package co.joebirch.minimise.remote

import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer

open class BaseApi {

    val client = HttpClient {
        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }
    }
}
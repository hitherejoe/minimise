package co.joebirch.minimise.remote

import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer

abstract class BaseApi(protected val client: HttpClient) {
    abstract val baseUrl: String
}
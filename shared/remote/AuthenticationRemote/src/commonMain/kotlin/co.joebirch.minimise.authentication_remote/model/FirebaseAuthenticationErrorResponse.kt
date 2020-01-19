package co.joebirch.minimise.authentication_remote.model

import kotlinx.serialization.Serializable

@Serializable
class FirebaseAuthenticationErrorResponse(
    val code: Int = 0,
    val message: String = ""
)
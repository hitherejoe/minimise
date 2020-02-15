package co.joebirch.minimise.authentication_remote.model

import kotlinx.serialization.Serializable

@Serializable
class FirebaseAuthenticationResponse(
    val kind: String = "",
    val idToken: String = "",
    val email: String = "",
    val refreshToken: String = "",
    val expiresIn: String = "",
    val localId: String = "",
    val code: Int = 0,
    val message: String = ""
)
package co.joebirch.firebase_auth_multiplatform.model

import kotlinx.serialization.Serializable

@Serializable
data class FirebaseCustomTokenSignInResponse(
    val idToken: String? = null,
    val refreshToken: String? = null,
    val expiresIn: String? = null,
    val code: Int? = null,
    val message: String? = null
)
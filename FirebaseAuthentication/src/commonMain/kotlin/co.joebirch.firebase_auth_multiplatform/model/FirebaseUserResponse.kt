package co.joebirch.firebase_auth_multiplatform.model

import kotlinx.serialization.Serializable

@Serializable
data class FirebaseUserResponse(
    val kind: String? = null,
    val users: List<FirebaseUser>? = null,
    val code: Int? = null,
    val message: String? = null
)
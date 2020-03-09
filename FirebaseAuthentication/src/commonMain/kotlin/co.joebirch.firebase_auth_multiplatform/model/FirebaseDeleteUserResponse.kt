package co.joebirch.firebase_auth_multiplatform.model

import kotlinx.serialization.Serializable

@Serializable
data class FirebaseDeleteUserResponse(
    val code: Int? = null,
    val message: String? = null
)
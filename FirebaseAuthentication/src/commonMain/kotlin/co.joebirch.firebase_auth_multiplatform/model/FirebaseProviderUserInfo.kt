package co.joebirch.firebase_auth_multiplatform.model

import kotlinx.serialization.Serializable

@Serializable
data class FirebaseProviderUserInfo(
    val providerId: String? = null,
    val federatedId: String? = null,
    val email: String? = null,
    val rawId: String? = null
)
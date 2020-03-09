package co.joebirch.firebase_auth_multiplatform.model

import kotlinx.serialization.Serializable

@Serializable
data class FirebaseUser(
    val localId: String? = null,
    val email: String? = null,
    val emailVerified: Boolean = false,
    val disabled: Boolean = false,
    val customAuth: Boolean = false,
    val displayName: String? = null,
    val providerUserInfo: List<FirebaseProviderUserInfo>? = null,
    val photoUrl: String? = null,
    val passwordHash: String? = null,
    val passwordUpdatedAt: Long? = null,
    val validSince: String? = null,
    val lastLoginAt: String? = null,
    val lastRefreshAt: String? = null,
    val createdAt: String? = null
)
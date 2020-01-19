package co.joebirch.minimise.shared_authentication.model

data class AuthenticationModel(
    val success: Boolean = true,
    val token: String? = null,
    val message: String? = null,
    val errorCode: Int? = null
)
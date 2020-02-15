package co.joebirch.minimise.authentication_remote.model

data class AuthenticationResponse(
    val success: Boolean = true,
    val token: String? = null,
    val message: String? = null,
    val errorCode: Int? = null
)
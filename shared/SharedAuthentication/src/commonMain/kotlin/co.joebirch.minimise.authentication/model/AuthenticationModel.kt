package co.joebirch.minimise.authentication.model

data class AuthenticationModel(
    val token: String? = null,
    val message: String? = null,
    val errorCode: Int? = null
)
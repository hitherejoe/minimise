package co.joebirch.minimise.authentication.model

data class AuthenticationResponseModel(
    val token: String? = null,
    val message: String? = null,
    val errorCode: Int? = null
)
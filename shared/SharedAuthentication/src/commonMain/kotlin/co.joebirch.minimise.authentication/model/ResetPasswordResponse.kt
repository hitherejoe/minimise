package co.joebirch.minimise.authentication.model

data class ResetPasswordResponse(
    val success: Boolean = false,
    val message: String? = null,
    val errorCode: Int? = null
)
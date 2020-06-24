package co.joebirch.minimise.authentication.model

data class ResetPasswordResponseModel(
    val success: Boolean = false,
    val message: String? = null,
    val errorCode: Int? = null
)
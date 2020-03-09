package co.joebirch.minimise.authentication.util

import javax.inject.Inject

class AuthenticationValidator @Inject constructor() {

    fun validateInputData(
        password: String,
        emailAddress: String
    ): Boolean {
        return password.isNotEmpty() && validateEmailAddress(emailAddress)
    }

    fun validateEmailAddress(
        emailAddress: String
    ): Boolean {
        return emailAddress.isNotEmpty()
    }
}
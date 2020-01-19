package co.joebirch.minimise.authentication

import android.util.Patterns

class AuthenticationValidator {

    fun validateInputData(
        password: String,
        emailAddress: String
    ): Boolean {
        return password.isNotEmpty() && emailAddress.isNotEmpty() &&
                Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()
    }
}
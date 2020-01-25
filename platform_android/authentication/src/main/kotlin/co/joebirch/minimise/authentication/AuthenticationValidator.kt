package co.joebirch.minimise.authentication

import javax.inject.Inject

class AuthenticationValidator @Inject constructor() {

    fun validateInputData(
        password: String,
        emailAddress: String
    ): Boolean {
        return password.isNotEmpty() && emailAddress.isNotEmpty()
    }
}
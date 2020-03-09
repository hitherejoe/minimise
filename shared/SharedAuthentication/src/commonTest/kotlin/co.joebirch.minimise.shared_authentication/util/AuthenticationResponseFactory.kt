package co.joebirch.minimise.shared_authentication.util

import co.joebirch.minimise.authentication_remote.model.FirebaseAuthenticationResponse
import co.joebirch.minimise.authentication.model.AuthenticationModel
import co.joebirch.minimise.shared_authentication.util.DataFactory.randomInt
import co.joebirch.minimise.shared_authentication.util.DataFactory.randomString

object AuthenticationResponseFactory {

    fun makeAuthenticationModel() =
        AuthenticationModel(
            true,
            randomString()
        )

    fun makeAuthenticationResponse(token: String = randomString()) =
        FirebaseAuthenticationResponse(randomString(), token, randomString(), randomString())

    fun makeAuthenticationModelForError() =
        AuthenticationModel(
            false,
            message = randomString(),
            errorCode = randomInt()
        )

    fun makeAuthenticationResponseForError(
        message: String = randomString(),
        errorCode: Int = randomInt()
    ) = AuthenticationModel(
        false,
        message = message,
        errorCode = errorCode
    )
}
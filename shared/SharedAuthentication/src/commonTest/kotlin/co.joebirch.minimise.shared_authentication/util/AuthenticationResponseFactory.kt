package co.joebirch.minimise.shared_authentication.util

import co.joebirch.minimise.authentication.model.AuthenticationModel
import co.joebirch.minimise.shared_authentication.util.DataFactory.randomInt
import co.joebirch.minimise.shared_authentication.util.DataFactory.randomString

object AuthenticationResponseFactory {

    fun makeAuthenticationModel() =
        AuthenticationModel(
            randomString()
        )

    fun makeAuthenticationResponse(token: String = randomString()) =
        AuthenticationModel(token, randomString(), randomInt())

    fun makeAuthenticationModelForError() =
        AuthenticationModel(
            null,
            message = randomString(),
            errorCode = randomInt()
        )

    fun makeAuthenticationResponseForError(
        message: String = randomString(),
        errorCode: Int = randomInt()
    ) = AuthenticationModel(
        null,
        message = message,
        errorCode = errorCode
    )
}
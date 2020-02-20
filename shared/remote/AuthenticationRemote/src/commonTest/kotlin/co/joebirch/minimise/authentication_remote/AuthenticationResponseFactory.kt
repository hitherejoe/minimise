package co.joebirch.minimise.authentication_remote

import co.joebirch.minimise.authentication_remote.DataFactory.randomString
import co.joebirch.minimise.authentication_remote.model.AuthenticationResponse

object AuthenticationResponseFactory {

    fun makeAuthenticationResponse(token: String = randomString()) =
        AuthenticationResponse(true, token)
}
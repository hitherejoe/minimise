package co.joebirch.firebase_auth_multiplatform

import co.joebirch.firebase_auth_multiplatform.DataFactory.randomBoolean
import co.joebirch.firebase_auth_multiplatform.DataFactory.randomInt
import co.joebirch.firebase_auth_multiplatform.DataFactory.randomLong
import co.joebirch.firebase_auth_multiplatform.DataFactory.randomString
import co.joebirch.firebase_auth_multiplatform.model.FirebaseAnonymousAuthenticationResponse
import co.joebirch.firebase_auth_multiplatform.model.FirebaseAuthenticationResponse
import co.joebirch.firebase_auth_multiplatform.model.*

object AuthenticationResponseFactory {

    fun makeAuthenticationResponse(token: String = randomString()) =
        FirebaseAuthenticationResponse(
            randomString(), token, randomString(), randomString(),
            randomString(), randomString(), randomInt(), randomString()
        )

    fun makeCustomTokenSignInResponse(token: String = randomString()) =
        FirebaseCustomTokenSignInResponse(
            randomString(), token, randomString(), randomInt(),
            randomString()
        )

    fun makeAnonymousSignInResponse(token: String = randomString()) =
        FirebaseAnonymousAuthenticationResponse(
            randomString(), token, randomString(),
            randomString(), randomInt(), randomString()
        )

    fun makeResetPasswordResponse(token: String = randomString()) =
        FirebasePasswordResetResponse(
            randomString(),
            token,
            randomInt(),
            randomString()
        )

    fun makeUpdateUserResponse(token: String = randomString()) =
        FirebaseUpdateUserResponse(
            randomString(), token, randomString(), randomString(),
            randomString(), randomString(), randomInt(), randomString()
        )

    fun makePasswordResetResponse(token: String = randomString()) =
        FirebasePasswordResetResponse(
            randomString(),
            token,
            randomInt(),
            randomString()
        )

    fun makeUserResponse() =
        FirebaseUserResponse(
            randomString(),
            listOf(
                makeUser()
            )
        )

    private fun makeUser() =
        FirebaseUser(
            randomString(), randomString(), randomBoolean(),
            randomBoolean(), randomBoolean(), randomString(), emptyList(), randomString(),
            randomString(), randomLong(), randomString(), randomString(), randomString(),
            randomString()
        )

    fun makeDeleteUserResponse() =
        FirebaseDeleteUserResponse(
            randomInt(),
            randomString()
        )

}
package co.joebirch.minimise.shared_authentication.util

import co.joebirch.firebase_auth_multiplatform.AuthenticationStore
import co.joebirch.firebase_auth_multiplatform.model.*

internal class MockAuthenticationStore : AuthenticationStore {

    lateinit var whenSignUp: (
        email: String,
        password: String,
        returnSecureToken: Boolean
    ) -> FirebaseAuthenticationResponse

    lateinit var whenSignIn: (
        email: String,
        password: String,
        returnSecureToken: Boolean
    ) -> FirebaseAuthenticationResponse

    override suspend fun signUp(
        apiKey: String,
        email: String,
        password: String,
        returnSecureToken: Boolean
    ) = whenSignUp(email, password, returnSecureToken)

    override suspend fun signIn(
        apiKey: String,
        email: String,
        password: String,
        returnSecureToken: Boolean
    ) = whenSignIn(email, password, returnSecureToken)

    override suspend fun signInWithCustomToken(
        apiKey: String,
        token: String,
        returnSecureToken: Boolean
    ): FirebaseCustomTokenSignInResponse {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun signInAnonymously(
        apiKey: String,
        returnSecureToken: Boolean
    ): FirebaseAnonymousAuthenticationResponse {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun resetPassword(
        apiKey: String,
        email: String,
        languageCode: String?
    ): FirebasePasswordResetResponse {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun verifyResetPassword(
        apiKey: String,
        oobCode: String
    ): FirebasePasswordResetResponse {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun confirmResetPassword(
        apiKey: String,
        oobCode: String,
        newPassword: String
    ): FirebasePasswordResetResponse {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun retrieveUser(apiKey: String, idToken: String): FirebaseUserResponse {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun deleteUser(apiKey: String, idToken: String): FirebaseDeleteUserResponse {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun updateEmail(
        apiKey: String,
        idToken: String,
        email: String,
        languageCode: String?,
        returnSecureToken: Boolean
    ): FirebaseUpdateUserResponse {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun updatePassword(
        apiKey: String,
        idToken: String,
        password: String,
        languageCode: String?,
        returnSecureToken: Boolean
    ): FirebaseUpdateUserResponse {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
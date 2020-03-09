package co.joebirch.firebase_auth_multiplatform

import co.joebirch.firebase_auth_multiplatform.model.FirebaseAuthenticationResponse
import co.joebirch.firebase_auth_multiplatform.model.*

public interface AuthenticationStore {

    suspend fun signUp(
        apiKey: String,
        email: String,
        password: String,
        returnSecureToken: Boolean
    ): FirebaseAuthenticationResponse

    suspend fun signIn(
        apiKey: String,
        email: String,
        password: String,
        returnSecureToken: Boolean
    ): FirebaseAuthenticationResponse

    suspend fun signInWithCustomToken(
        apiKey: String,
        token: String,
        returnSecureToken: Boolean
    ): FirebaseCustomTokenSignInResponse

    suspend fun signInAnonymously(
        apiKey: String,
        returnSecureToken: Boolean
    ): FirebaseAnonymousAuthenticationResponse

    suspend fun resetPassword(
        apiKey: String,
        email: String,
        languageCode: String? = null
    ): FirebasePasswordResetResponse

    suspend fun verifyResetPassword(
        apiKey: String,
        oobCode: String
    ): FirebasePasswordResetResponse

    suspend fun confirmResetPassword(
        apiKey: String,
        oobCode: String,
        newPassword: String
    ): FirebasePasswordResetResponse

    suspend fun retrieveUser(
        apiKey: String,
        idToken: String
    ): FirebaseUserResponse

    suspend fun deleteUser(
        apiKey: String,
        idToken: String
    ): FirebaseDeleteUserResponse

    suspend fun updateEmail(
        apiKey: String,
        idToken: String,
        email: String,
        languageCode: String? = null,
        returnSecureToken: Boolean = true
    ): FirebaseUpdateUserResponse

    suspend fun updatePassword(
        apiKey: String,
        idToken: String,
        password: String,
        languageCode: String? = null,
        returnSecureToken: Boolean = true
    ): FirebaseUpdateUserResponse
}
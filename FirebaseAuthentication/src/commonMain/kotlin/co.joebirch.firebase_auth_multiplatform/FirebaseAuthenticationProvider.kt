package co.joebirch.firebase_auth_multiplatform

import co.joebirch.firebase_auth_multiplatform.AuthenticationStore
import co.joebirch.firebase_auth_multiplatform.FirebaseAuthenticationStore
import co.joebirch.firebase_auth_multiplatform.HttpClientProvider

object FirebaseAuthenticationProvider {
    fun createAuthenticationRemote(): AuthenticationStore =
        FirebaseAuthenticationStore(
            HttpClientProvider().httpClient
        )
}
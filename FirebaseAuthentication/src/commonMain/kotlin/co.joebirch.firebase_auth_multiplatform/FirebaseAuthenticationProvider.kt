package co.joebirch.firebase_auth_multiplatform

object FirebaseAuthenticationProvider {
    fun createAuthenticationRemote(): AuthenticationStore =
        FirebaseAuthenticationStore(
            HttpClientProvider().httpClient
        )
}
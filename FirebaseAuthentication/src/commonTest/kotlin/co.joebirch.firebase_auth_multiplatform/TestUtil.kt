package co.joebirch.firebase_auth_multiplatform

@Suppress("UnusedPrivateMember")
internal expect fun <T> runTest(block: suspend () -> T)
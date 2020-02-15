package co.joebirch.minimise.shared_authentication

@Suppress("UnusedPrivateMember")
internal expect fun <T> runTest(block: suspend () -> T)
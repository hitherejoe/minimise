package co.joebirch.minimise.authentication_remote

@Suppress("UnusedPrivateMember")
internal expect fun <T> runTest(block: suspend () -> T)
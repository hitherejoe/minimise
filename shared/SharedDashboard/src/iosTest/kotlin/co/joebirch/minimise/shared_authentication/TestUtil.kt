package co.joebirch.minimise.shared_authentication

import kotlinx.coroutines.runBlocking

internal actual fun <T> runTest(block: suspend () -> T) {
    runBlocking { block() }
}
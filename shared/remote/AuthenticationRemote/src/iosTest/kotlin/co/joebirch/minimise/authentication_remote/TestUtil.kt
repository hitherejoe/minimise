package co.joebirch.minimise.authentication_remote

import kotlinx.coroutines.runBlocking

internal actual fun <T> runTest(block: suspend () -> T) {
    runBlocking { block() }
}
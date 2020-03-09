package co.joebirch.firebase_auth_multiplatform

import kotlinx.coroutines.runBlocking

internal actual fun <T> runTest(block: suspend () -> T) {
    runBlocking { block() }
}
package co.joebirch.firebase_auth_multiplatform

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest

@UseExperimental(ExperimentalCoroutinesApi::class)
internal actual fun <T> runTest(block: suspend () -> T) {
    runBlockingTest { block() }
}
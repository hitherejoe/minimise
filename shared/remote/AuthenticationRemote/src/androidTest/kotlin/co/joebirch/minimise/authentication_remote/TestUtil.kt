package co.joebirch.minimise.authentication_remote

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest

@UseExperimental(ExperimentalCoroutinesApi::class)
internal actual fun <T> runTest(block: suspend () -> T) {
    runBlockingTest { block() }
}
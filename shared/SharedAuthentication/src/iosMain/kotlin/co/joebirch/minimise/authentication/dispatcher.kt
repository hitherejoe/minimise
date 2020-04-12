package co.joebirch.minimise.authentication

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.newSingleThreadContext

actual val dispatcher = newSingleThreadContext("DefaultDispatcher") as CoroutineDispatcher
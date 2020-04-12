package co.joebirch.minimise.authentication

import co.joebirch.minimise.shared_common.ApplicationDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.newSingleThreadContext

actual val dispatcher = ApplicationDispatcher
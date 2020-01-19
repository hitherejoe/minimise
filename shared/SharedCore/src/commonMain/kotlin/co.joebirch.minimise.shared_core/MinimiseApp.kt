package co.joebirch.minimise.shared_core

import co.joebirch.minimise.authentication_remote.authenticationRemoteModule
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin

object MinimiseApp {

    fun startKoin() {
        startKoin {
            loadKoinModules(authenticationRemoteModule)
        }
    }
}
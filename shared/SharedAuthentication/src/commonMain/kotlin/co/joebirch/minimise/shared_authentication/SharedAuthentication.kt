package co.joebirch.minimise.shared_authentication

import org.koin.core.context.loadKoinModules

object SharedAuthentication {

    fun initialize() {
        loadKoinModules(authenticationSharedModule)
    }
}
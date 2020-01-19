package co.joebirch.minimise.authentication_remote

import co.joebirch.minimise.shared_authentication.store.AuthenticationRemote
import org.koin.dsl.module

val authenticationRemoteModule = module {

    factory<AuthenticationRemote> { AuthenticationApi("") }
}
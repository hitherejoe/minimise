package co.joebirch.minimise.shared_authentication

import org.koin.dsl.module

val authenticationSharedModule = module {

    factory<AuthenticationRepository> { AuthenticationDataRepository(get()) }
}
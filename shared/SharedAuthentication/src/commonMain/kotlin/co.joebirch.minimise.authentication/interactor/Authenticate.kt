package co.joebirch.minimise.authentication.interactor

import co.joebirch.minimise.authentication.AuthenticationRepository
import co.joebirch.minimise.shared_common.interactor.UseCase
import co.joebirch.minimise.authentication.model.AuthenticationModel
import co.joebirch.minimise.authentication.AuthenticateMode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

open class Authenticate constructor() : UseCase<AuthenticationModel, Authenticate.Params>() {

    private lateinit var authenticationRepository: AuthenticationRepository

    constructor(authenticationRepository: AuthenticationRepository) : this()  {
        this.authenticationRepository = authenticationRepository
    }

    init {
        if (!::authenticationRepository.isInitialized) {
            authenticationRepository = AuthenticationRepository.create()
        }
    }

    override fun run(params: Params, completion: (AuthenticationModel) -> Unit) {
        GlobalScope.launch {
            val result = withContext(Dispatchers.Default) {
                when (params.authenticateMode) {
                    AuthenticateMode.SignUp ->
                        authenticationRepository.signUp(params.apiKey, params.emailAddress,
                            params.password)
                    AuthenticateMode.SignIn ->
                        authenticationRepository.signIn(params.apiKey, params.emailAddress,
                            params.password)
                }
            }
            withContext(Dispatchers.Main) {
                completion(result)
            }
        }
    }

    override fun runWithoutThreading(params: Params, completion: (AuthenticationModel) -> Unit) {
        GlobalScope.launch(Dispatchers.Main) {
            val result =  when (params.authenticateMode) {
                AuthenticateMode.SignUp ->
                    authenticationRepository.signUp(params.apiKey, params.emailAddress,
                        params.password)
                AuthenticateMode.SignIn ->
                    authenticationRepository.signIn(params.apiKey, params.emailAddress,
                        params.password)
            }
            completion(result)
        }
    }

    open class Params private constructor(
        val authenticateMode: AuthenticateMode,
        val apiKey: String,
        val emailAddress: String,
        val password: String
    ) {
        companion object {

            fun forSignUp(
                apiKey: String,
                emailAddress: String,
                password: String
            ) =
                Params(
                    AuthenticateMode.SignUp,
                    apiKey,
                    emailAddress,
                    password
                )

            fun forSignIn(
                apiKey: String,
                emailAddress: String,
                password: String
            ) =
                Params(
                    AuthenticateMode.SignIn,
                    apiKey,
                    emailAddress,
                    password
                )
        }
    }
}
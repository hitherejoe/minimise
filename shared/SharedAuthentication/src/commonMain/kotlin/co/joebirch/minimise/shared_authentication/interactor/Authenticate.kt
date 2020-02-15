package co.joebirch.minimise.shared_authentication.interactor

import co.joebirch.minimise.shared_authentication.AuthenticationRepository
import co.joebirch.minimise.shared_common.interactor.UseCase
import co.joebirch.minimise.shared_authentication.model.AuthenticationModel
import co.joebirch.minimise.shared_authentication.presentation.AuthenticateMode
import co.joebirch.minimise.shared_common.ApplicationDispatcher
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

open class Authenticate constructor() : UseCase<AuthenticationModel, Authenticate.Params>() {

    private lateinit var authenticationRepository: AuthenticationRepository

    constructor(authenticationRepository: AuthenticationRepository) : this()  {
        this.authenticationRepository = authenticationRepository
    }

    init {
        if (!::authenticationRepository.isInitialized) {
            authenticationRepository = AuthenticationRepository.get()
        }
    }

    override fun run(params: Params, completion: (AuthenticationModel) -> Unit) {
        GlobalScope.launch(ApplicationDispatcher) {
            completion.invoke(
                when (params.authenticateMode) {
                    AuthenticateMode.SignUp ->
                        authenticationRepository.signUp(params.emailAddress, params.password)
                    AuthenticateMode.SignIn ->
                        authenticationRepository.signIn(params.emailAddress, params.password)
                }
            )
        }
    }

    open class Params private constructor(
        val authenticateMode: AuthenticateMode,
        val emailAddress: String,
        val password: String
    ) {
        companion object {

            fun forSignUp(
                emailAddress: String,
                password: String
            ) =
                Params(
                    AuthenticateMode.SignUp,
                    emailAddress,
                    password
                )

            fun forSignIn(
                emailAddress: String,
                password: String
            ) =
                Params(
                    AuthenticateMode.SignIn,
                    emailAddress,
                    password
                )
        }
    }
}
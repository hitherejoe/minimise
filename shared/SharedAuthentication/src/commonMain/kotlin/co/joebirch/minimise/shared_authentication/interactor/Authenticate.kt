package co.joebirch.minimise.shared_authentication.interactor

import co.joebirch.minimise.shared_authentication.AuthenticationRepository
import co.joebirch.minimise.shared_common.interactor.UseCase
import co.joebirch.minimise.shared_authentication.model.AuthenticationModel
import co.joebirch.minimise.shared_authentication.presentation.AuthenticateMode
import co.joebirch.minimise.shared_common.ApplicationDispatcher
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.core.context.GlobalContext.get

open class Authenticate : UseCase<AuthenticationModel, Authenticate.Params>() {

    private var authenticationRepository: AuthenticationRepository = get().get()

    override fun run(params: Params, completion: (AuthenticationModel) -> Unit) {
        GlobalScope.launch(ApplicationDispatcher) {
            completion.invoke(
                when (params.authenticateMode) {
                    AuthenticateMode.SIGN_UP ->
                        authenticationRepository.signUp(params.emailAddress, params.password)
                    AuthenticateMode.SIGN_IN ->
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
                    AuthenticateMode.SIGN_UP,
                    emailAddress,
                    password
                )

            fun forSignIn(
                emailAddress: String,
                password: String
            ) =
                Params(
                    AuthenticateMode.SIGN_IN,
                    emailAddress,
                    password
                )
        }
    }
}
package co.joebirch.minimise.authentication.interactor

import co.joebirch.minimise.authentication.AuthenticationRepository
import co.joebirch.minimise.shared_common.interactor.UseCase
import co.joebirch.minimise.authentication.model.ResetPasswordResponse
import co.joebirch.minimise.shared_common.ApplicationDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

open class ResetPassword constructor() : UseCase<ResetPasswordResponse, ResetPassword.Params>() {

    private lateinit var authenticationRepository: AuthenticationRepository

    constructor(authenticationRepository: AuthenticationRepository) : this()  {
        this.authenticationRepository = authenticationRepository
    }

    init {
        if (!::authenticationRepository.isInitialized) {
            authenticationRepository = AuthenticationRepository.create()
        }
    }

    override fun run(params: Params, completion: (ResetPasswordResponse) -> Unit) {
        GlobalScope.launch(ApplicationDispatcher) {
            completion(authenticationRepository.resetPassword(params.apiKey, params.emailAddress))
        }
    }

    override fun runOnNative(params: Params, completion: (ResetPasswordResponse) -> Unit) {
        GlobalScope.launch(Dispatchers.Main) {
            completion(authenticationRepository.resetPassword(params!!.apiKey, params.emailAddress))
        }
    }

    open class Params private constructor(
        val apiKey: String,
        val emailAddress: String
    ) {
        companion object {

            fun forResetPassword(
                apiKey: String,
                emailAddress: String
            ) =
                Params(
                    apiKey,
                    emailAddress
                )
        }
    }
}
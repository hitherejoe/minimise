package co.joebirch.minimise.authentication.mapper

import co.joebirch.minimise.SignInQuery
import co.joebirch.minimise.SignUpMutation
import co.joebirch.minimise.authentication.model.AuthenticationModel

fun SignInQuery.Login.toAuthenticationModel(): AuthenticationModel {
    return this.asAuthData?.let {
        AuthenticationModel(it.token)
    } ?: run {
        AuthenticationModel(message = this.asAuthenticationError?.message)
    }
}

fun SignUpMutation.CreateUser.toAuthenticationModel(): AuthenticationModel {
    return this.asAuthData?.let {
        AuthenticationModel(it.token)
    } ?: run {
        AuthenticationModel(message = this.asAuthenticationError?.message)
    }
}
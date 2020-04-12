package co.joebirch.minimise.authentication

import co.joebirch.minimise.authentication.firebase.AuthenticationStore
import co.joebirch.minimise.authentication.mapper.AuthenticationResponseMapper
import co.joebirch.minimise.authentication.mapper.ResetPasswordResponseMapper

object RepositoriesProvider {
     fun getAuthenticationDataRepository(
         remote: AuthenticationStore,
         mapper: AuthenticationResponseMapper,
         passwordResponseMapper: ResetPasswordResponseMapper
     ): AuthenticationRepository =
         AuthenticationDataRepository(
             remote,
             mapper,
             passwordResponseMapper
         )
}
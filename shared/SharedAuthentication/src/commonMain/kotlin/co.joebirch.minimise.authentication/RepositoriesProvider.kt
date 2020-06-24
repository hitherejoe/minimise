package co.joebirch.minimise.authentication

import co.joebirch.minimise.authentication.mapper.AuthenticationResponseMapper
import co.joebirch.minimise.authentication.mapper.ResetPasswordResponseMapper
import co.joebirch.minimise.authentication.remote.AuthenticationRemote

object RepositoriesProvider {
     fun getAuthenticationDataRepository(
         remote: AuthenticationRemote,
         mapper: AuthenticationResponseMapper,
         passwordResponseMapper: ResetPasswordResponseMapper
     ): AuthenticationRepository =
         AuthenticationDataRepository(
             remote,
             mapper,
             passwordResponseMapper
         )
}
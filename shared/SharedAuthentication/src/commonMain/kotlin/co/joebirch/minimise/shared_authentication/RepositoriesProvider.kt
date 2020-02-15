package co.joebirch.minimise.shared_authentication

import co.joebirch.minimise.authentication_remote.AuthenticationRemote
import co.joebirch.minimise.shared_authentication.mapper.AuthenticationResponseMapper

object RepositoriesProvider {
     fun getAuthenticationDataRepository(
         remote: AuthenticationRemote,
         mapper: AuthenticationResponseMapper
     ): AuthenticationRepository =
         AuthenticationDataRepository(remote, mapper)
}
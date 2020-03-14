package co.joebirch.minimise.shared_authentication.mapper

import co.joebirch.firebase_auth_multiplatform.model.FirebasePasswordResetResponse
import co.joebirch.minimise.authentication.mapper.ResetPasswordResponseMapper
import co.joebirch.minimise.authentication.model.ResetPasswordResponse

internal class MockResetPasswordResponseMapper : ResetPasswordResponseMapper() {

    lateinit var whenMapFromResetPasswordResponse:
                (response: FirebasePasswordResetResponse) -> ResetPasswordResponse

}
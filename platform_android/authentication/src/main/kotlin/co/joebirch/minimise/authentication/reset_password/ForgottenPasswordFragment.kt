package co.joebirch.minimise.authentication.reset_password

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import co.joebirch.minimise.authentication.util.AuthenticationValidator

class ForgottenPasswordFragment constructor(
    private val authenticationValidator: AuthenticationValidator
) : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}

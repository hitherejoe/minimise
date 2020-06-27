package co.joebirch.minimise.authentication.reset_password

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import co.joebirch.minimise.android.core.di.ViewModelFactory
import javax.inject.Inject

class ForgottenPasswordFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val resetPasswordViewModel: ResetPasswordViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FrameLayout(requireContext()).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )

        }
    }
}

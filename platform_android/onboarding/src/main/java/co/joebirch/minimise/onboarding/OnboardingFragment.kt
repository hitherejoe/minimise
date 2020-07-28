package co.joebirch.minimise.onboarding

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import co.joebirch.minimise.android.core.di.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingFragment : BaseFragment() {

    private val onboardingViewModel: OnboardingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = onboardingViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //binding.continueButton.setOnClickListener {
        //    viewModel.navigate(OnboardingDirections.Authentication)
      //  }
    }

}

package co.joebirch.minimise.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import co.joebirch.minimise.android.core.di.BaseFragment
import co.joebirch.minimise.android.core.di.ViewModelFactory
import co.joebirch.minimise.navigation.OnboardingDirections
import co.joebirch.minimise.onboarding.databinding.FragmentOnboardingBinding
import co.joebirch.minimise.onboarding.di.inject
import javax.inject.Inject

class OnboardingFragment : BaseFragment() {

    @Inject lateinit var viewModelFactory: ViewModelFactory
    private lateinit var binding: FragmentOnboardingBinding
    private val timeToPostViewModel: OnboardingViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject(this)
        viewModel = timeToPostViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOnboardingBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.continueButton.setOnClickListener {
            viewModel.navigate(OnboardingDirections.Authentication)
        }
    }

}

package co.joebirch.minimise.creation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.viewModels
import co.joebirch.creation_ui.composeDashboardContent
import co.joebirch.minimise.android.core.di.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreationFragment : BaseFragment() {

    private val dashboardViewModel: CreationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = dashboardViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FrameLayout(requireContext()).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )

            composeDashboardContent(
                viewLifecycleOwner,
                dashboardViewModel.observeAuthenticationState(),
                dashboardViewModel::setProductName,
                dashboardViewModel::setStoreName,
                dashboardViewModel::setFrequency,
                dashboardViewModel::setRemindDays,
                dashboardViewModel::setPositives,
                dashboardViewModel::setNegatives,
                dashboardViewModel::navigateToNextStep,
                dashboardViewModel::navigateToPreviousStep,
                dashboardViewModel::createBelonging
            )
        }
    }
}
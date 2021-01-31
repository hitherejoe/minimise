package co.joebirch.minimise.creation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import co.joebirch.minimise.android.core.di.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreationFragment : BaseFragment() {

    private val dashboardViewModel: CreationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (dashboardViewModel.currentStep() > 0) {
                        dashboardViewModel.navigateToPreviousStep()
                    } else {
                     //   NavHostFragment.findNavController(
                       //     this@CreationFragment).navigateUp()
                    }
                }
            })

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

            composeDashboardContent(
                dashboardViewModel.uiState
            ) { dashboardViewModel.handleCreationEvent(it)  }
        }
    }
}
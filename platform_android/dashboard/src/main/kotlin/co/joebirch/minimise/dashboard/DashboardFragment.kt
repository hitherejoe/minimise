package co.joebirch.minimise.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.viewModels
import co.joebirch.minimise.android.core.di.BaseFragment
import co.joebirch.minimise.navigation.DashboardDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : BaseFragment() {

    private val dashboardViewModel: DashboardViewModel by viewModels()

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
                dashboardViewModel.uiState,
                listOf(Category.PendingBelongings, Category.Belongings),
                { category -> dashboardViewModel.setSelectedCategory(category) },
                ::navigateToCreation
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dashboardViewModel.getBelongings("fj")
    }

    fun navigateToCreation() {
        viewModel.navigate(DashboardDirections.Creation)
    }
}
package co.joebirch.minimise.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.viewModels
import co.joebirch.minimise.android.core.di.BaseFragment
import co.joebirch.minimise.android.core.di.ViewModelFactory
import co.joebirch.minimise.dashboard.di.inject
import co.joebirch.minimise.navigation.AuthenticationDirections
import co.joebirch.minimise.navigation.DashboardDirections
import javax.inject.Inject

class DashboardFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val dashboardViewModel: DashboardViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject(this)
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
                listOf(Category.PendingBelongings, Category.Belongings),
                { category -> dashboardViewModel.setSelectedCategory(category) },
                ::navigateToCreation
            )
        }
    }

    fun navigateToCreation() {
        viewModel.navigate(DashboardDirections.Creation)
    }
}
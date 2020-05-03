package co.joebirch.minimise.creation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.viewModels
import co.joebirch.minimise.android.core.di.BaseFragment
import co.joebirch.minimise.android.core.di.ViewModelFactory
import co.joebirch.minimise.android.core.di.coreComponent
import co.joebirch.minimise.creation.di.component.DaggerCreationComponent
import javax.inject.Inject

class CreationFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val dashboardViewModel: CreationViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerCreationComponent
            .builder()
            .coreComponent(coreComponent())
            .build()
            .inject(this)
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
        }
    }
}
package co.joebirch.minimise.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import co.joebirch.minimise.android.core.di.ViewModelFactory
import co.joebirch.minimise.android.core.di.coreComponent
import co.joebirch.minimise.android.di.component.DaggerMainComponent
import co.joebirch.minimise.authentication.di.component.DaggerAuthenticationComponent
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: MainViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DaggerMainComponent
            .builder()
            .coreComponent(coreComponent())
            .build()
            .inject(this)

        viewModel.observeState().observe(this, Observer {
            when (it) {
                is MainState.Authenticated -> {
                    findNavController(R.id.hostFragment).also {  controller ->
                        controller.graph = controller.navInflater.inflate(R.navigation.dashboard)
                    }
                }
                is MainState.NotAuthenticated -> {
                    findNavController(R.id.hostFragment).also {  controller ->
                        controller.graph = controller.navInflater.inflate(R.navigation.nav_graph)
                    }
                }
            }
        })
    }
}

package co.joebirch.minimise.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.observeState().observe(this, Observer {
            when (it) {
                is MainState.Authenticated -> {
                    findNavController(R.id.hostFragment).also {  controller ->
                        controller.graph = controller.navInflater.inflate(R.navigation.dashboard)
                    }
                }
                is MainState.NotAuthenticated -> {
                    findNavController(R.id.hostFragment).also {  controller ->
                        controller.graph = controller.navInflater.inflate(R.navigation.dashboard)
                    }
                }
                MainState.Loading -> {

                }
            }
        })
    }
}

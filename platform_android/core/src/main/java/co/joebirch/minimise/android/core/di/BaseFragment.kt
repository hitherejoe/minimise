package co.joebirch.minimise.android.core.di

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import co.joebirch.minimise.navigation.NavigationCommand

abstract class BaseFragment : Fragment() {

    lateinit var viewModel: BaseViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.navigationCommands.observe(viewLifecycleOwner, Observer { command ->
            when (command) {
                is NavigationCommand.ToAndFinishActivity -> {
                    findNavController().navigate(command.directions)
                    activity?.finish()
                }
                is NavigationCommand.To ->
                    findNavController().navigate(command.directions)
            }
        })
    }
}
package co.joebirch.minimise.authentication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.joebirch.minimise.authentication.databinding.FragmentForgottenPasswordBinding

class ForgottenPasswordFragment : Fragment() {

    private lateinit var binding: FragmentForgottenPasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentForgottenPasswordBinding.inflate(layoutInflater)
        return binding.root
    }

}

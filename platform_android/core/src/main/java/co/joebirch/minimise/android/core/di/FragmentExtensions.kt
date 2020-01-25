package co.joebirch.minimise.android.core.di

import androidx.fragment.app.Fragment
import co.joebirch.minimise.android.core.di.coreComponent

fun Fragment.coreComponent() = requireActivity().coreComponent()
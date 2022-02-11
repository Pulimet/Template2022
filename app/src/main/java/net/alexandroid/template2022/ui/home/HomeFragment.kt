package net.alexandroid.template2022.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import net.alexandroid.template2022.R
import net.alexandroid.template2022.databinding.FragmentHomeBinding
import net.alexandroid.template2022.ui.binding.FragmentBinding

class HomeFragment : Fragment(R.layout.fragment_home) {
    private val binding by FragmentBinding(FragmentHomeBinding::bind)
    private val homeViewModel by viewModels<HomeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel.onFragmentViewCreated()
    }
}
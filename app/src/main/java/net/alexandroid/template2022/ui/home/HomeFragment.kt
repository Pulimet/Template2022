package net.alexandroid.template2022.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import net.alexandroid.template2022.R
import net.alexandroid.template2022.databinding.FragmentHomeBinding
import net.alexandroid.template2022.ui.NavViewModel
import net.alexandroid.template2022.ui.binding.FragmentBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(R.layout.fragment_home) {
    private val binding by FragmentBinding(FragmentHomeBinding::bind)
    private val viewModel by viewModel<HomeViewModel>()
    private val navViewModel by sharedViewModel<NavViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.onFragmentViewCreated()

        binding.btnOpenExample.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_exampleFragment)
        }
    }
}
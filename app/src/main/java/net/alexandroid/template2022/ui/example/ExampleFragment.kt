package net.alexandroid.template2022.ui.example

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import net.alexandroid.template2022.R
import net.alexandroid.template2022.databinding.FragmentExampleBinding
import net.alexandroid.template2022.ui.NavViewModel
import net.alexandroid.template2022.ui.binding.FragmentBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ExampleFragment : Fragment(R.layout.fragment_example) {
    private val binding by FragmentBinding(FragmentExampleBinding::bind)
    private val viewModel by viewModel<ExampleViewModel>()
    private val navViewModel by sharedViewModel<NavViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
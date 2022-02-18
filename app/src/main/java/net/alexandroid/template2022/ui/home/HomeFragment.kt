package net.alexandroid.template2022.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import net.alexandroid.template2022.R
import net.alexandroid.template2022.databinding.FragmentHomeBinding
import net.alexandroid.template2022.ui.binding.FragmentBinding
import net.alexandroid.template2022.ui.navigation.NavViewModel
import net.alexandroid.template2022.utils.setOnClickListeners
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(R.layout.fragment_home), View.OnClickListener {
    private val binding by FragmentBinding(FragmentHomeBinding::bind)
    private val viewModel by viewModel<HomeViewModel>()
    private val navViewModel by sharedViewModel<NavViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.navViewModel = navViewModel
        setClickListener()
    }

    private fun setClickListener() {
        setOnClickListeners(
            binding.btnOpenFragment,
            binding.btnOpenActivity,
            binding.btnMovies
        )
    }

    // View.OnClickListener
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnOpenFragment -> viewModel.onBtnOpenFragmentClick()
            R.id.btnOpenActivity -> viewModel.onBtnOpenActivityClick()
            R.id.btnMovies -> viewModel.onBntMoviesClick()
        }
    }
}
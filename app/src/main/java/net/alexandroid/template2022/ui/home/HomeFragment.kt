package net.alexandroid.template2022.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.alexandroid.template2022.R
import net.alexandroid.template2022.databinding.FragmentHomeBinding
import net.alexandroid.template2022.network.services.TmdbApiService
import net.alexandroid.template2022.ui.binding.FragmentBinding
import net.alexandroid.template2022.ui.example.ExampleActivity
import net.alexandroid.template2022.ui.navigation.NavViewModel
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(R.layout.fragment_home), View.OnClickListener {
    private val binding by FragmentBinding(FragmentHomeBinding::bind)
    private val viewModel by viewModel<HomeViewModel>()
    private val navViewModel by sharedViewModel<NavViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListener()
    }

    private fun setClickListener() {
        binding.btnOpenFragment.setOnClickListener(this)
        binding.btnOpenActivity.setOnClickListener(this)
        binding.btnNetworkTest.setOnClickListener(this)
    }

    // View.OnClickListener
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnOpenFragment -> {
                navViewModel.navigateTo(HomeFragmentDirections.actionMainFragmentToExampleFragment())
            }
            R.id.btnOpenActivity -> {
                navViewModel.startActivity(ExampleActivity::class.java)
            }
            R.id.btnNetworkTest -> {
                lifecycleScope.launch(Dispatchers.IO) {
                    val service = get<TmdbApiService>()
                    val result = service.getMovies()
                    withContext(Dispatchers.Main) {
                        binding.tvText.text = result.results[0].title
                    }
                }
            }
        }
    }
}
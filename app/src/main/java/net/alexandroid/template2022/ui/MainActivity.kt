package net.alexandroid.template2022.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import net.alexandroid.template2022.databinding.ActivityMainBinding
import net.alexandroid.template2022.utils.launchAndCollect
import net.alexandroid.template2022.utils.logD
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModel<MainViewModel>()
    private val navViewModel by viewModel<NavViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setViewBinding()
        mainViewModel.onActivityOnCreate()
        navViewModel.onActivityOnCreate()

        mainViewModel.uiState.launchAndCollect(this) {
            logD("It: $it")
        }
    }

    private fun setViewBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
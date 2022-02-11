package net.alexandroid.template2022.di

import android.content.Context
import net.alexandroid.template2022.ui.MainViewModel
import net.alexandroid.template2022.ui.home.HomeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

object Di {
    fun setup(applicationContext: Context) {
        startKoin {
            KoinLogs()
            androidContext(applicationContext)
            modules(appModule)
        }
    }

    private val appModule = module {

        viewModel { MainViewModel() }
        viewModel { HomeViewModel() }

    }
}
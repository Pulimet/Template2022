package net.alexandroid.template2022.di

import android.content.Context
import net.alexandroid.template2022.network.NetworkConstants
import net.alexandroid.template2022.network.services.TmdbApiService
import net.alexandroid.template2022.network.utils.NetworkObjectsCreator
import net.alexandroid.template2022.network.utils.NetworkObjectsCreator.createWebService
import net.alexandroid.template2022.ui.MainViewModel
import net.alexandroid.template2022.ui.navigation.NavViewModel
import net.alexandroid.template2022.ui.example.ExampleViewModel
import net.alexandroid.template2022.ui.home.HomeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

object Di {
    fun setup(applicationContext: Context) {
        startKoin {
            logger(KoinLogs())
            androidContext(applicationContext)
            modules(appModule, networkModule)
        }
    }

    private val appModule = module {
        viewModel { NavViewModel() }
        viewModel { MainViewModel() }
        viewModel { HomeViewModel() }
        viewModel { ExampleViewModel() }
    }

    private val networkModule = module {
        single { NetworkObjectsCreator.createOkHttpClient() }
        single { createWebService<TmdbApiService>(get(), NetworkConstants.TMDB_URL) }
    }
}
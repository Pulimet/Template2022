package net.alexandroid.template2022.di

import android.content.Context
import androidx.room.Room
import net.alexandroid.template2022.db.MovieDatabase
import net.alexandroid.template2022.network.NetworkConstants
import net.alexandroid.template2022.network.services.TmdbApiService
import net.alexandroid.template2022.network.utils.NetworkObjectsCreator
import net.alexandroid.template2022.network.utils.NetworkObjectsCreator.createWebService
import net.alexandroid.template2022.repo.MoviesRepo
import net.alexandroid.template2022.ui.MainViewModel
import net.alexandroid.template2022.ui.example.ExampleViewModel
import net.alexandroid.template2022.ui.home.HomeViewModel
import net.alexandroid.template2022.ui.movies.MoviesListViewModel
import net.alexandroid.template2022.ui.navigation.NavViewModel
import net.alexandroid.template2022.utils.OkHttpLogs
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

object Di {
    fun setup(applicationContext: Context) {
        startKoin {
            logger(KoinLogs())
            androidContext(applicationContext)
            modules(appModule, networkModule, dbModule)
        }
    }

    private val appModule = module {
        single { MoviesRepo(get()) }

        viewModel { NavViewModel() }
        viewModel { MainViewModel() }
        viewModel { HomeViewModel(get()) }
        viewModel { ExampleViewModel() }
        viewModel { MoviesListViewModel(get()) }
    }

    private val networkModule = module {
        single<HttpLoggingInterceptor.Logger> { OkHttpLogs() }
        single { NetworkObjectsCreator.createOkHttpClient(get()) }
        single { createWebService<TmdbApiService>(get(), NetworkConstants.TMDB_URL) }
    }

    private val dbModule = module {
        single {
            Room.databaseBuilder(
                androidContext(), MovieDatabase::class.java, "movies_database"
            ).build()
        }
        single { get<MovieDatabase>().movieDao() }
    }
}
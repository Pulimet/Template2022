package net.alexandroid.template2022.di

import android.content.Context
import androidx.room.Room
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import net.alexandroid.template2022.db.MovieDatabase
import net.alexandroid.template2022.di.Prefs.rating
import net.alexandroid.template2022.di.Prefs.votes
import net.alexandroid.template2022.network.NetworkConstants
import net.alexandroid.template2022.network.services.TmdbApiService
import net.alexandroid.template2022.network.utils.NetworkObjectsCreator
import net.alexandroid.template2022.network.utils.NetworkObjectsCreator.createWebService
import net.alexandroid.template2022.repo.MovieSettingsRepo
import net.alexandroid.template2022.repo.MoviesRepo
import net.alexandroid.template2022.ui.MainViewModel
import net.alexandroid.template2022.ui.example.ExampleViewModel
import net.alexandroid.template2022.ui.home.HomeViewModel
import net.alexandroid.template2022.ui.movies.details.MovieDetailsViewModel
import net.alexandroid.template2022.ui.movies.favorites.MovieFavoritesViewModel
import net.alexandroid.template2022.ui.movies.list.MoviesListViewModel
import net.alexandroid.template2022.ui.movies.settings.MovieSettingsViewModel
import net.alexandroid.template2022.ui.navigation.NavViewModel
import net.alexandroid.template2022.utils.OkHttpLogs
import net.alexandroid.template2022.utils.logE
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module
import kotlin.coroutines.CoroutineContext

object Di {
    fun setup(applicationContext: Context) {
        startKoin {
            logger(KoinLogs())
            androidContext(applicationContext)
            modules(appModule, networkModule, dbModule)
        }
    }

    private val exceptionHandler = CoroutineExceptionHandler { _, e ->
        logE("CoroutineExceptionHandler:", t = e)
    }

    private val appModule = module {
        // DataStore
        single(named(Prefs.MOVIE_VOTES)) { androidContext().votes }
        single(named(Prefs.MOVIE_RATING)) { androidContext().rating }

        // Repositories
        single {
            MoviesRepo(
                get(),
                get(),
                get(),
                get(named(Prefs.MOVIE_VOTES)),
                get(named(Prefs.MOVIE_RATING))
            )
        }
        single { MovieSettingsRepo(get(named(Prefs.MOVIE_VOTES)), get(named(Prefs.MOVIE_RATING))) }

        // CoroutineContext
        single { Dispatchers.IO + exceptionHandler }

        // ViewModels
        viewModel { NavViewModel() }
        viewModel { MainViewModel() }
        viewModel { HomeViewModel() }
        viewModel { ExampleViewModel() }
        viewModel { MoviesListViewModel(get(), get()) }
        viewModel { MovieDetailsViewModel(get()) }
        viewModel { MovieFavoritesViewModel(get()) }
        viewModel { MovieSettingsViewModel(get(), get()) }
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
        single { get<MovieDatabase>().movieFavoriteDao() }
    }
}
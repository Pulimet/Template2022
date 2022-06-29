package net.alexandroid.template2022.di

import android.content.Context
import androidx.room.Room
import androidx.work.WorkManager
import coil.ImageLoader
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import net.alexandroid.template2022.db.db.ApiDatabase
import net.alexandroid.template2022.db.db.MovieDatabase
import net.alexandroid.template2022.di.Prefs.rating
import net.alexandroid.template2022.di.Prefs.votes
import net.alexandroid.template2022.network.NetworkConstants
import net.alexandroid.template2022.network.services.ApiCallerService
import net.alexandroid.template2022.network.services.TmdbApiService
import net.alexandroid.template2022.network.utils.NetworkObjectsCreator
import net.alexandroid.template2022.network.utils.NetworkObjectsCreator.createWebService
import net.alexandroid.template2022.repo.api.ApiRepo
import net.alexandroid.template2022.repo.movie.MovieSettingsRepo
import net.alexandroid.template2022.repo.movie.MoviesRepo
import net.alexandroid.template2022.ui.MainViewModel
import net.alexandroid.template2022.ui.api.ApiCaller
import net.alexandroid.template2022.ui.api.add.ApiAddViewModel
import net.alexandroid.template2022.ui.api.list.ApiListViewModel
import net.alexandroid.template2022.ui.api.schedule.ScheduleApiViewModel
import net.alexandroid.template2022.ui.example.ExampleViewModel
import net.alexandroid.template2022.ui.home.HomeViewModel
import net.alexandroid.template2022.ui.movies.details.MovieDetailsViewModel
import net.alexandroid.template2022.ui.movies.favorites.MovieFavoritesViewModel
import net.alexandroid.template2022.ui.movies.list.MoviesListViewModel
import net.alexandroid.template2022.ui.movies.settings.MovieSettingsViewModel
import net.alexandroid.template2022.ui.navigation.NavViewModel
import net.alexandroid.template2022.utils.ImageLoading
import net.alexandroid.template2022.utils.GetResource
import net.alexandroid.template2022.utils.logs.*
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import kotlin.system.measureTimeMillis

object Di {
    fun setup(applicationContext: Context) {
        val timeInMillis = measureTimeMillis {
            startKoin {
                logger(KoinLogs())
                androidContext(applicationContext)
                modules(
                    appModule, viewModelsModule, reposModule, networkModule, dbModule, imageModule
                )
            }
        }
        logI("=== DI is ready (timeInMillis: $timeInMillis)===")
    }


    private val appModule = module {
        // Resources
        singleOf(::GetResource)

        // DataStore
        single(named(Prefs.MOVIE_VOTES)) { androidContext().votes }
        single(named(Prefs.MOVIE_RATING)) { androidContext().rating }

        // WorkManager
        single { WorkManager.getInstance(androidContext()) }

        // CoroutineContext
        single {
            Dispatchers.IO + CoroutineExceptionHandler { _, e ->
                logE("CoroutineExceptionHandler:", t = e)
                throw e
            }
        }
    }

    private val viewModelsModule = module {
        viewModelOf(::NavViewModel)
        viewModelOf(::MainViewModel)
        viewModelOf(::HomeViewModel)
        viewModelOf(::ExampleViewModel)

        // Movies
        viewModelOf(::MoviesListViewModel)
        viewModelOf(::MovieDetailsViewModel)
        viewModelOf(::MovieFavoritesViewModel)
        viewModelOf(::MovieSettingsViewModel)

        // Api
        viewModelOf(::ApiListViewModel)
        viewModelOf(::ApiAddViewModel)
        viewModelOf(::ScheduleApiViewModel)
    }

    private val reposModule = module {
        // Repositories
        single {
            MoviesRepo(
                get(),
                get(),
                get(),
                get(named(Prefs.MOVIE_VOTES)),
                get(named(Prefs.MOVIE_RATING)),
                get()
            )
        }
        single { MovieSettingsRepo(get(named(Prefs.MOVIE_VOTES)), get(named(Prefs.MOVIE_RATING))) }
        singleOf(::ApiCaller)
        singleOf(::ApiRepo)
    }

    private val imageModule = module {
        single {
            ImageLoader.Builder(androidContext())
                .logger(CoilLogs())
                .crossfade(true)
                .build()
        }
        single { ImageLoading(androidContext(), get()) }
    }

    private val networkModule = module {
        single<HttpLoggingInterceptor.Logger> { OkHttpLogs() }
        single { NetworkObjectsCreator.createOkHttpClient(get()) }
        single { createWebService<TmdbApiService>(get(), NetworkConstants.TMDB_URL) }
        single { createWebService<ApiCallerService>(get(), NetworkConstants.GOOGLE_URL) }
    }

    private val dbModule = module {
        single {
            Room.databaseBuilder(
                androidContext(), MovieDatabase::class.java, "movies_database"
            ).build()
        }
        single {
            Room.databaseBuilder(
                androidContext(), ApiDatabase::class.java, "api_database"
            ).build()
        }
        single { get<MovieDatabase>().movieDao() }
        single { get<MovieDatabase>().movieFavoriteDao() }
        single { get<ApiDatabase>().apiDao() }
    }
}
package net.alexandroid.template2022.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

object Prefs {
    const val MOVIE_VOTES = "MoviesVotes"
    const val MOVIE_RATING = "MoviesRatings"

    val Context.votes: DataStore<Preferences> by preferencesDataStore(name = MOVIE_VOTES)
    val Context.rating: DataStore<Preferences> by preferencesDataStore(name = MOVIE_RATING)
}
package net.alexandroid.template2022.repo

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class MovieSettingsRepo(
    private val dataStoreMinVotes: DataStore<Preferences>,
    private val dataStoreMinRating: DataStore<Preferences>
) {

    companion object {
        val KEY_MIN_VOTES = intPreferencesKey("minVotes")
        val KEY_MIN_RATING = intPreferencesKey("minRating")

        private const val DEFAULT_MIN_VOTES = 5
        private const val DEFAULT_MIN_RATING = 2
    }

    // Min votes
    val getMinVotes: Flow<Int> = dataStoreMinVotes.data
        .catch { exception ->
            // dataStore.data throws an IOException when an error is encountered when reading data
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[KEY_MIN_VOTES] ?: DEFAULT_MIN_VOTES
        }


    suspend fun saveMinVotes(minVotes: Int) {
        dataStoreMinVotes.edit { preferences ->
            preferences[KEY_MIN_VOTES] = minVotes
        }
    }

    // Min rating
    val getMinRating: Flow<Int> = dataStoreMinRating.data
        .catch { exception ->
            // dataStore.data throws an IOException when an error is encountered when reading data
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[KEY_MIN_RATING] ?: DEFAULT_MIN_RATING
        }


    suspend fun saveMinRating(minVotes: Int) {
        dataStoreMinRating.edit { preferences ->
            preferences[KEY_MIN_RATING] = minVotes
        }
    }
}
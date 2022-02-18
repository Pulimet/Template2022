package net.alexandroid.template2022.ui.movies.settings

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import net.alexandroid.template2022.repo.MovieSettingsRepo
import net.alexandroid.template2022.ui.base.BaseViewModel
import net.alexandroid.template2022.ui.navigation.NavViewModel

class MovieSettingsViewModel(
    private val settingsRepo: MovieSettingsRepo,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : BaseViewModel() {
    lateinit var navViewModel: NavViewModel

    // Min votes
    val minVotesLiveData =
        settingsRepo.getMinVotes.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), 0)

    fun onBtnMinusVotesNumClick() {
        viewModelScope.launch(ioDispatcher) {
            val current = minVotesLiveData.value
            val minus = when {
                current > 300 -> 100
                current > 50 -> 50
                current > 10 -> 10
                else -> 5
            }
            if (current - minus > -1) settingsRepo.saveMinVotes(current - minus)
        }
    }

    fun onBtnPlusVotesNumClick() {
        viewModelScope.launch(ioDispatcher) {
            val current = minVotesLiveData.value
            val add = when {
                current > 250 -> 100
                current > 40 -> 50
                current > 5 -> 10
                else -> 5
            }
            settingsRepo.saveMinVotes(current + add)
        }
    }

    // Min rating
    val minRatingLiveData =
        settingsRepo.getMinRating.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), 0)

    fun onBtnMinusRatingClick() {
        viewModelScope.launch(ioDispatcher) {
            val current = minRatingLiveData.value
            if (current > 0) settingsRepo.saveMinRating(current - 1)
        }
    }

    fun onBtnPlusRatingClick() {
        viewModelScope.launch(ioDispatcher) {
            val current = minRatingLiveData.value
            if (current < 9) settingsRepo.saveMinRating(current + 1)
        }
    }
}
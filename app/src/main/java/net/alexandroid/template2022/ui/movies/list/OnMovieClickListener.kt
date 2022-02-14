package net.alexandroid.template2022.ui.movies.list

import androidx.navigation.fragment.FragmentNavigator
import net.alexandroid.template2022.db.model.Movie

interface OnMovieClickListener {
    fun onClick(movie: Movie, extras: FragmentNavigator.Extras, position: Int)
}
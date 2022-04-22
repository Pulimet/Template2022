package net.alexandroid.template2022.ui.movies.recycler

import androidx.recyclerview.widget.DiffUtil
import net.alexandroid.template2022.db.model.movies.Movie

class MovieDiff : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Movie, newItem: Movie) = oldItem == newItem
}
package net.alexandroid.template2022.ui.movies.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import net.alexandroid.template2022.R
import net.alexandroid.template2022.db.model.Movie

class MovieAdapter(private val listener: OnMovieClickListener) :
    ListAdapter<Movie, MovieHolder>(MovieDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieHolder(v, listener)
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        holder.onBindViewHolder(getItem(position))
    }
}

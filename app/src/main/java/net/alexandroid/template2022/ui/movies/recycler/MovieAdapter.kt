package net.alexandroid.template2022.ui.movies.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import net.alexandroid.template2022.R
import net.alexandroid.template2022.db.model.Movie
import net.alexandroid.template2022.ui.movies.recycler.MovieHolder
import net.alexandroid.template2022.ui.movies.recycler.OnMovieClickListener

class MovieAdapter(private val listener: OnMovieClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var data = listOf<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieHolder(v, listener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MovieHolder).onBindViewHolder(data[position])
    }

    override fun getItemCount() = data.size

    fun setItems(items: List<Movie>) {
        data = items
        notifyDataSetChanged()
    }
}

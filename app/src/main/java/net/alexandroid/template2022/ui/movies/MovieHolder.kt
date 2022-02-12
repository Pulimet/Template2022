package net.alexandroid.template2022.ui.movies

import android.view.View
import androidx.core.view.ViewCompat
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import net.alexandroid.template2022.databinding.ItemMovieBinding
import net.alexandroid.template2022.db.model.Movie

class MovieHolder(v: View, private val listener: OnMovieClickListener) :
    RecyclerView.ViewHolder(v), View.OnClickListener {

    init {
        itemView.setOnClickListener(this)
    }

    private val binding = ItemMovieBinding.bind(v)
    private var movie: Movie? = null

    fun onBindViewHolder(movie: Movie) {
        this.movie = movie
        setVotes(movie)
        loadImage(movie)
        ViewCompat.setTransitionName(binding.imgMovie, "image_${movie.id}")
    }

    private fun setVotes(movie: Movie) {
        val votesText = "${movie.vote} (${movie.voteCount})"
        binding.tvVotes.apply {
            visibility = View.GONE
            text = votesText
        }
    }

    private fun loadImage(movie: Movie) {
        movie.posterUrl?.let {
            binding.imgMovie.load(it) {
                crossfade(true)
                scale(Scale.FILL)
                listener(onSuccess = { _, _ ->
                    binding.tvVotes.visibility = View.VISIBLE
                })
            }
        }
    }

    // View.OnClickListener
    override fun onClick(v: View?) {
        if (adapterPosition != RecyclerView.NO_POSITION) {
            val extras = FragmentNavigatorExtras(
                binding.imgMovie to "image_${movie?.id}"
            )
            movie?.let { listener.onClick(it, extras, adapterPosition) }
        }
    }
}
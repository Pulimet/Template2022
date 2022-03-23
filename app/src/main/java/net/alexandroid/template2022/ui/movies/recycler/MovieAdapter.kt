package net.alexandroid.template2022.ui.movies.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import coil.ImageLoader
import coil.request.ImageRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.alexandroid.template2022.R
import net.alexandroid.template2022.db.model.Movie

class MovieAdapter(
    private val listener: OnMovieClickListener,
    private val context: Context,
    private val imageLoader: ImageLoader
) : ListAdapter<Movie, MovieHolder>(MovieDiff()), CoroutineScope {

    override val coroutineContext = Dispatchers.Main

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieHolder(v, listener)
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        holder.onBindViewHolder(getItem(position), imageLoader)
    }

    override fun submitList(list: List<Movie>?) {
        super.submitList(list)
        launch {
            delay(2000)
            preloadImages(list)
        }
    }

    private fun preloadImages(list: List<Movie>?) {
        list?.forEach {
            val request = ImageRequest.Builder(context)
                .data(it.posterUrl)
                // Optional, but setting a ViewSizeResolver will conserve memory by limiting the size the image should be preloaded into memory at.
                //.size(ViewSizeResolver(imageView))
                .build()
            imageLoader.enqueue(request)
        }
    }
}

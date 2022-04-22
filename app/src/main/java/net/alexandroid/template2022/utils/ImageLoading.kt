package net.alexandroid.template2022.utils

import android.content.Context
import coil.ImageLoader
import coil.request.ImageRequest
import net.alexandroid.template2022.db.model.movies.Movie

class ImageLoading(private val context: Context, private val imageLoader: ImageLoader) {
    fun preloadImages(list: List<Movie>?) {
        list?.forEach {
            if (it.posterUrl == null) return@forEach
            val request = ImageRequest.Builder(context)
                .data(it.posterUrl)
                // Optional, but setting a ViewSizeResolver will conserve memory by limiting the size the image should be preloaded into memory at.
                //.size(ViewSizeResolver(imageView))
                .build()
            imageLoader.enqueue(request)
        }
    }
}
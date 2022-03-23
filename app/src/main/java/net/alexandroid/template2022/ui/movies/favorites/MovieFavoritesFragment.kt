package net.alexandroid.template2022.ui.movies.favorites

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.alexandroid.template2022.R
import net.alexandroid.template2022.databinding.FragmentMovieFavoritesBinding
import net.alexandroid.template2022.db.model.Movie
import net.alexandroid.template2022.ui.binding.FragmentBinding
import net.alexandroid.template2022.ui.movies.recycler.MovieAdapter
import net.alexandroid.template2022.ui.movies.recycler.OnMovieClickListener
import net.alexandroid.template2022.ui.navigation.NavViewModel
import net.alexandroid.template2022.utils.collectIt
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieFavoritesFragment : Fragment(R.layout.fragment_movie_favorites), OnMovieClickListener {
    companion object {
        private const val POSITION_FOR_DELAY = 2
    }

    private val binding by FragmentBinding(FragmentMovieFavoritesBinding::bind)
    private val viewModel by viewModel<MovieFavoritesViewModel>()
    private val navViewModel by sharedViewModel<NavViewModel>()
    private val imageLoader by inject<ImageLoader>()

    private var gridLayoutManager: GridLayoutManager? = null
    private var movieAdapter: MovieAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.navViewModel = navViewModel
        setRecyclerView()
        observeViewModel()
    }

    private fun setRecyclerView() {
        binding.homeRecyclerView.apply {
            layoutManager = GridLayoutManager(context, 2).apply { gridLayoutManager = this }
            adapter = MovieAdapter(
                this@MovieFavoritesFragment,
                requireContext(),
                imageLoader
            ).apply { movieAdapter = this }

            if (viewModel.savedItemPosition >= 0) {
                // Solves return transition animation
                postponeEnterTransition()
            }
        }
    }

    private fun observeViewModel() {
        viewModel.favoritesList.collectIt(viewLifecycleOwner) {
            movieAdapter?.submitList(it)
            scrollToPreviouslyClickedItem(gridLayoutManager)  // Scrolls to position of selected item on going back to the list
        }
    }

    private fun scrollToPreviouslyClickedItem(layoutManager: RecyclerView.LayoutManager?) {
        if (viewModel.savedItemPosition < 0) return
        lifecycleScope.launch {
            if (viewModel.savedItemPosition > POSITION_FOR_DELAY) {
                delay(80) // Without this delay scrollToPosition function not working
                layoutManager?.scrollToPosition(viewModel.savedItemPosition)
                delay(10)
                startPostponedEnterTransition()
            } else {
                startPostponedEnterTransition()
            }
        }
    }

    // OnMovieClickListener
    override fun onClick(movie: Movie, extras: FragmentNavigator.Extras, position: Int) {
        viewModel.saveClickedItemPosition(position)
        viewModel.onUserMovieClick(movie, extras)
    }
}
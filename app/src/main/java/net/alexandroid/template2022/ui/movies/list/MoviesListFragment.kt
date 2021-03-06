package net.alexandroid.template2022.ui.movies.list

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
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
import net.alexandroid.template2022.databinding.FragmentMoviesListBinding
import net.alexandroid.template2022.db.model.movies.Movie
import net.alexandroid.template2022.repo.movie.MovieResult
import net.alexandroid.template2022.ui.binding.FragmentBinding
import net.alexandroid.template2022.ui.movies.recycler.MovieAdapter
import net.alexandroid.template2022.ui.movies.recycler.OnMovieClickListener
import net.alexandroid.template2022.ui.navigation.NavViewModel
import net.alexandroid.template2022.utils.collectIt
import net.alexandroid.template2022.utils.logs.logD
import net.alexandroid.template2022.utils.showToast
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoviesListFragment : Fragment(R.layout.fragment_movies_list), OnMovieClickListener {
    companion object {
        private const val POSITION_FOR_DELAY = 2
    }

    private val binding by FragmentBinding(FragmentMoviesListBinding::bind)
    private val viewModel by viewModel<MoviesListViewModel>()
    private val navViewModel by sharedViewModel<NavViewModel>()
    private val imageLoader by inject<ImageLoader>()

    private var gridLayoutManager: GridLayoutManager? = null
    private var movieAdapter: MovieAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        // TODO Switch to MenuProvider
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.navViewModel = navViewModel
        setRecyclerView()
        setSwipeRefreshLayout()
        observeViewModel()
        viewModel.onFragmentViewCreated()
    }

    private fun setRecyclerView() {
        binding.homeRecyclerView.apply {
            layoutManager = GridLayoutManager(context, 2).apply { gridLayoutManager = this }
            adapter = MovieAdapter(this@MoviesListFragment, imageLoader)
                .apply { movieAdapter = this }

            if (viewModel.savedItemPosition >= 0) {
                // Solves return transition animation
                postponeEnterTransition()
            }
        }
    }

    private fun setSwipeRefreshLayout() {
        binding.swipeRefreshLayout.isRefreshing = true
        binding.swipeRefreshLayout.setOnRefreshListener { viewModel.onUserRefreshedMain() }
    }

    private fun observeViewModel() {
        viewModel.uiState.collectIt(viewLifecycleOwner) {
            when (it) {
                is MovieResult.Empty -> logD("Empty")
                is MovieResult.Loading -> logD("Loading...")
                is MovieResult.Success -> handleDbResultSuccess(it)
                is MovieResult.Error -> logD("Error")
            }
        }
    }

    private fun handleDbResultSuccess(it: MovieResult.Success) {
        logD()
        movieAdapter?.submitList(it.moviesList)
        scrollToPreviouslyClickedItem(gridLayoutManager)  // Scrolls to position of selected item on going back to the list
        if (it.moviesList.isNotEmpty()) binding.swipeRefreshLayout.isRefreshing = false
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
        viewModel.onUserMovieClick(movie, extras, position)
    }

    // Menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.movies, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            R.id.action_settings -> {
                val position = gridLayoutManager?.findLastVisibleItemPosition() ?: 0
                viewModel.onSettingsClick(position)
                true
            }
            R.id.action_favorites -> {
                val position = gridLayoutManager?.findLastVisibleItemPosition() ?: 0
                viewModel.onFavoritesClick(position)
                true
            }
            R.id.action_schedule -> {
                viewModel.onMenuScheduleClick()
                showToast(R.string.toast_schedule_movies_fetch)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

}
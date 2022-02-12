package net.alexandroid.template2022.ui.movies

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.alexandroid.template2022.R
import net.alexandroid.template2022.databinding.FragmentMoviesListBinding
import net.alexandroid.template2022.db.model.Movie
import net.alexandroid.template2022.repo.MovieResult
import net.alexandroid.template2022.ui.binding.FragmentBinding
import net.alexandroid.template2022.ui.navigation.NavViewModel
import net.alexandroid.template2022.utils.collectIt
import net.alexandroid.template2022.utils.logD
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoviesListFragment : Fragment(R.layout.fragment_movies_list), OnMovieClickListener {
    private val binding by FragmentBinding(FragmentMoviesListBinding::bind)
    private val viewModel by viewModel<MoviesListViewModel>()
    private val navViewModel by sharedViewModel<NavViewModel>()

    private var gridLayoutManager: GridLayoutManager? = null
    private var homeAdapter: HomeAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        setSwipeRefreshLayout()
        observeViewModel()
        viewModel.fetchMovies()
    }

    private fun setRecyclerView() {
        binding.homeRecyclerView.apply {
            layoutManager = GridLayoutManager(context, 2).apply { gridLayoutManager = this }
            adapter = HomeAdapter(this@MoviesListFragment).apply { homeAdapter = this }

            // Solves return transition animation
            postponeEnterTransition()
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
                is MovieResult.Success -> handleResultSuccess(it)
                is MovieResult.Error -> logD("Error")
            }
        }
    }

    private fun handleResultSuccess(it: MovieResult.Success) {
        homeAdapter?.setItems(it.moviesList)
        scrollToPreviouslyClickedItem(gridLayoutManager)  // Scrolls to position of selected item on going back to the list
        if (it.moviesList.isNotEmpty()) binding.swipeRefreshLayout.isRefreshing = false
    }

    private fun scrollToPreviouslyClickedItem(layoutManager: RecyclerView.LayoutManager?) {
        lifecycleScope.launch {
            if (viewModel.savedItemPosition > 4) {
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
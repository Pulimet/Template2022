package net.alexandroid.template2022.ui.movies.details

import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import coil.load
import net.alexandroid.template2022.R
import net.alexandroid.template2022.databinding.FragmentMovieDetailsBinding
import net.alexandroid.template2022.ui.binding.FragmentBinding
import net.alexandroid.template2022.ui.navigation.NavViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieDetailsFragment : Fragment(R.layout.fragment_movie_details) {
    private val binding by FragmentBinding(FragmentMovieDetailsBinding::bind)
    private val viewModel by viewModel<MovieDetailsViewModel>()
    private val navViewModel by sharedViewModel<NavViewModel>()

    private val args: MovieDetailsFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.navViewModel = navViewModel

        fillMovieData()
        ViewCompat.setTransitionName(binding.imgMoviePoster, "image_${args.movie.id}")
    }

    private fun fillMovieData() {
        args.movie.let {
            binding.imgMoviePoster.load(it.posterUrl)
            binding.tvTitle.text = it.getTitleWithYear()
            binding.tvDescription.text = it.overview
            binding.tvRating.text = String.format("Rating: %s", it.vote.toString())
        }
    }
}
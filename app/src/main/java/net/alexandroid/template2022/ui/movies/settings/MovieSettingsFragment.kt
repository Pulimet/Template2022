package net.alexandroid.template2022.ui.movies.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import net.alexandroid.template2022.R
import net.alexandroid.template2022.databinding.FragmentMovieSettingsBinding
import net.alexandroid.template2022.ui.binding.FragmentBinding
import net.alexandroid.template2022.ui.navigation.NavViewModel
import net.alexandroid.template2022.utils.collectIt
import net.alexandroid.template2022.utils.setOnClickListeners
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieSettingsFragment : Fragment(R.layout.fragment_movie_settings), View.OnClickListener {
    private val binding by FragmentBinding(FragmentMovieSettingsBinding::bind)
    private val viewModel by viewModel<MovieSettingsViewModel>()
    private val navViewModel by sharedViewModel<NavViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.navViewModel = navViewModel
        observeViewModel()
        setClickListener()
    }

    private fun observeViewModel() {
        viewModel.apply {
            minVotesLiveData.collectIt(viewLifecycleOwner) {
                binding.tvMinVotesValue.text = it.toString()
            }
            minRatingLiveData.collectIt(viewLifecycleOwner) {
                binding.tvMinRatingValue.text = it.toString()
            }
        }
    }

    private fun setClickListener() {
        setOnClickListeners(
            binding.btnMinusVotes,
            binding.btnPlusVotes,
            binding.btnMinusRating,
            binding.btnPlusRating
        )
    }

    //  View.OnClickListener
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnMinusVotes -> viewModel.onBtnMinusVotesNumClick()
            R.id.btnPlusVotes -> viewModel.onBtnPlusVotesNumClick()
            R.id.btnMinusRating -> viewModel.onBtnMinusRatingClick()
            R.id.btnPlusRating -> viewModel.onBtnPlusRatingClick()
        }
    }
}
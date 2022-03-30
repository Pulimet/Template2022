package net.alexandroid.template2022.ui.api.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import net.alexandroid.template2022.R
import net.alexandroid.template2022.databinding.FragmentApiListBinding
import net.alexandroid.template2022.ui.binding.FragmentBinding
import net.alexandroid.template2022.ui.navigation.NavViewModel
import net.alexandroid.template2022.utils.setOnClickListeners
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ApiListFragment : Fragment(R.layout.fragment_api_list), View.OnClickListener {
    private val binding by FragmentBinding(FragmentApiListBinding::bind)
    private val viewModel by viewModel<ApiListViewModel>()
    private val navViewModel by sharedViewModel<NavViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.navViewModel = navViewModel
        setClickListener()
    }

    private fun setClickListener() {
        setOnClickListeners(
            binding.fabAddApi
        )
    }

    //  View.OnClickListener
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.fabAddApi -> viewModel.onFabAddApiClick()
        }
    }
}
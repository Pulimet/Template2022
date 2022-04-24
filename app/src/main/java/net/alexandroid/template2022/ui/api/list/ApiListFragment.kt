package net.alexandroid.template2022.ui.api.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import net.alexandroid.template2022.R
import net.alexandroid.template2022.databinding.FragmentApiListBinding
import net.alexandroid.template2022.ui.api.list.recycler.ApiAdapter
import net.alexandroid.template2022.ui.api.list.recycler.OnApiAction
import net.alexandroid.template2022.ui.binding.FragmentBinding
import net.alexandroid.template2022.ui.navigation.NavViewModel
import net.alexandroid.template2022.utils.logs.logD
import net.alexandroid.template2022.utils.setOnClickListeners
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ApiListFragment : Fragment(R.layout.fragment_api_list), View.OnClickListener, OnApiAction {
    private val binding by FragmentBinding(FragmentApiListBinding::bind)
    private val viewModel by viewModel<ApiListViewModel>()
    private val navViewModel by sharedViewModel<NavViewModel>()
    private var apiAdapter: ApiAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.navViewModel = navViewModel
        setClickListener()
        setApisRecyclerView()
        observeViewModel()
    }

    private fun setClickListener() {
        setOnClickListeners(binding.fabAddApi)
    }

    private fun setApisRecyclerView() {
        binding.apisRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = ApiAdapter(this@ApiListFragment).apply { apiAdapter = this }
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.apiList().flowWithLifecycle(lifecycle).collect {
                logD("size: ${it.size}")
                it.forEach { api ->
                    logD("${api.baseUrl} - Params: ${api.params}")
                }
                apiAdapter?.submitList(it)
            }
        }
    }

    //  View.OnClickListener
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.fabAddApi -> viewModel.onFabAddApiClick()
        }
    }

    // OnApiAction
    override fun onClick() {

    }
}
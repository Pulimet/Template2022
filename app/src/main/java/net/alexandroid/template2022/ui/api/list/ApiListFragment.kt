package net.alexandroid.template2022.ui.api.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import net.alexandroid.template2022.R
import net.alexandroid.template2022.databinding.FragmentApiListBinding
import net.alexandroid.template2022.db.model.api.Api
import net.alexandroid.template2022.ui.api.list.recycler.ApiAdapter
import net.alexandroid.template2022.ui.api.list.recycler.OnApiAction
import net.alexandroid.template2022.ui.binding.FragmentBinding
import net.alexandroid.template2022.ui.navigation.NavViewModel
import net.alexandroid.template2022.utils.collectIt
import net.alexandroid.template2022.utils.setOnClickListeners
import net.alexandroid.template2022.utils.showToast
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
        viewModel.apply {
            showToast.collectIt(viewLifecycleOwner) { showToast(it) }
            apiList()
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .onEach {
                    apiAdapter?.submitList(it)
                }
                .launchIn(lifecycleScope)
        }
    }

    // View.OnClickListener
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.fabAddApi -> viewModel.onFabAddApiClick()
        }
    }

    // OnApiAction
    override fun onClick(api: Api) {
        viewModel.onApiClick(api)
    }

    override fun onBtnEditClick(api: Api) {
        viewModel.onBtnEditApi(api)
    }

    override fun onBtnDeleteClick(api: Api) {
        viewModel.onBtnDeleteApi(api)
    }

    override fun onBtnScheduleClick(api: Api) {
        viewModel.onBtnScheduleApi(api)
    }
}
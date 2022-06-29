package net.alexandroid.template2022.ui.api.add

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import net.alexandroid.template2022.R
import net.alexandroid.template2022.databinding.FragmentAddApiBinding
import net.alexandroid.template2022.db.model.api.Param
import net.alexandroid.template2022.ui.api.add.dialog.AddParamDialog
import net.alexandroid.template2022.ui.api.add.dialog.ImportUrlDialog
import net.alexandroid.template2022.ui.api.add.recycler.OnParamAction
import net.alexandroid.template2022.ui.api.add.recycler.ParamsAdapter
import net.alexandroid.template2022.ui.binding.FragmentBinding
import net.alexandroid.template2022.ui.navigation.NavViewModel
import net.alexandroid.template2022.utils.collectIt
import net.alexandroid.template2022.utils.logs.logD
import net.alexandroid.template2022.utils.setOnClickListeners
import net.alexandroid.template2022.utils.showToast
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddApiFragment : Fragment(R.layout.fragment_add_api), View.OnClickListener,
    AddParamDialog.SubmitParamCallBack, ImportUrlDialog.SubmitUrlCallBack, OnParamAction {

    private val binding by FragmentBinding(FragmentAddApiBinding::bind)
    private val args: AddApiFragmentArgs by navArgs()
    private val viewModel by viewModel<ApiAddViewModel>()
    private val navViewModel by sharedViewModel<NavViewModel>()
    private val addParamDialog = AddParamDialog(this)
    private val importUrlDialog = ImportUrlDialog(this)
    private var paramsAdapter: ParamsAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.navViewModel = navViewModel

        lifecycle.addObserver(addParamDialog)
        lifecycle.addObserver(importUrlDialog)

        observeViewModel()
        setListeners()
        setParamsRecyclerView()
        setValuesIfArgumentExist()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        lifecycle.removeObserver(addParamDialog)
    }

    private fun observeViewModel() {
        viewModel.apply {
            showAddParamDialog.collectIt(viewLifecycleOwner) {
                addParamDialog.show(requireContext(), it)
            }
            showImportUrlDialog.collectIt(viewLifecycleOwner) {
                importUrlDialog.show(requireContext())
            }
            addBtnState.collectIt(viewLifecycleOwner) { binding.fabAddParam.isEnabled = it }
            saveBtnState.collectIt(viewLifecycleOwner) { binding.fabSave.isEnabled = it }
            paramsList.collectIt(viewLifecycleOwner) {
                logD("Observed params list change: $it")
                paramsAdapter?.submitList(it)
            }
            showToast.collectIt(viewLifecycleOwner) { showToast(it) }
            baseUrl.collectIt(viewLifecycleOwner) {
                if (it.isNotEmpty()) binding.etBaseUrl.setText(it)
            }
            baseUrlError.collectIt(viewLifecycleOwner) {
                binding.tilBaseUrl.error = if (it == 0) "" else getString(it)
            }
        }
    }

    private fun setListeners() {
        setOnClickListeners(binding.fabSave, binding.fabAddParam, binding.fabImport)

        binding.etBaseUrl.addTextChangedListener {
            it?.let { url -> viewModel.onBaseUrlChanged(url.toString()) }
        }
    }

    private fun setParamsRecyclerView() {
        binding.paramsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = ParamsAdapter(this@AddApiFragment).apply { paramsAdapter = this }
        }
    }

    private fun setValuesIfArgumentExist() {
        args.api?.let { viewModel.loadArgumentApi(it) }
    }

    //  View.OnClickListener
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.fabSave -> viewModel.onSaveBtnClick(binding.etBaseUrl.text.toString(), args.api)
            R.id.fabAddParam -> viewModel.onAddParamBtnClick()
            R.id.fabImport -> viewModel.onImportBtnClick()
        }
    }

    // AddParamDialog.SubmitCallBack
    override fun onSubmitParam(param: Param, isEditMode: Param?) {
        viewModel.onSubmitParam(param, isEditMode)
    }

    // ImportUrlDialog.SubmitUrlCallBack
    override fun onSubmitUrl(url: String) {
        viewModel.onSubmitImportingUrl(url)
    }

    // OnParamAction
    override fun onBtnEditClick(param: Param) {
        viewModel.onBtnEditParamClick(param)
    }

    override fun onBtnDeleteClick(param: Param) {
        viewModel.onBtnDeleteParamClick(param)
    }
}
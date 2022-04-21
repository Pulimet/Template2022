package net.alexandroid.template2022.ui.api.add

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import net.alexandroid.template2022.R
import net.alexandroid.template2022.databinding.FragmentAddApiBinding
import net.alexandroid.template2022.ui.binding.FragmentBinding
import net.alexandroid.template2022.ui.navigation.NavViewModel
import net.alexandroid.template2022.utils.collectIt
import net.alexandroid.template2022.utils.setOnClickListeners
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ApiAddFragment : Fragment(R.layout.fragment_add_api), View.OnClickListener,
    AddParamDialog.SubmitCallBack {

    private val binding by FragmentBinding(FragmentAddApiBinding::bind)
    private val viewModel by viewModel<ApiAddViewModel>()
    private val navViewModel by sharedViewModel<NavViewModel>()
    private val addParamDialog = AddParamDialog(this)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.navViewModel = navViewModel
        lifecycle.addObserver(addParamDialog)
        observeViewModel()
        setListeners()
    }

    private fun observeViewModel() {
        viewModel.apply {
            showDialog.collectIt(viewLifecycleOwner) { if (it) addParamDialog.show(requireContext()) }
            addBtnState.collectIt(viewLifecycleOwner) { binding.fabAddParam.isEnabled = it }
            saveBtnState.collectIt(viewLifecycleOwner) { binding.fabSave.isEnabled = it }
            baseUrlError.collectIt(viewLifecycleOwner) {
                binding.tilBaseUrl.error = if (it == 0) "" else getString(it)
            }
        }
    }

    private fun setListeners() {
        setOnClickListeners(binding.fabSave, binding.fabAddParam, binding.fabImport)

        binding.tilBaseUrl.editText?.addTextChangedListener {
            it?.let { url -> viewModel.onBaseUrlStateChange(url.toString()) }
        }
    }

    //  View.OnClickListener
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.fabSave -> viewModel.onSaveBtnClick()
            R.id.fabAddParam -> viewModel.onAddParamBtnClick()
            R.id.fabImport -> viewModel.onImportBtnClick()
        }
    }

    // AddParamDialog.SubmitCallBack
    override fun onSubmit(key: String, value: String) {
        viewModel.onNewParamSubmit(key, value)
    }
}
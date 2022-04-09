package net.alexandroid.template2022.ui.api.add

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import net.alexandroid.template2022.R
import net.alexandroid.template2022.databinding.FragmentAddApiBinding
import net.alexandroid.template2022.ui.binding.FragmentBinding
import net.alexandroid.template2022.ui.navigation.NavViewModel
import net.alexandroid.template2022.utils.setOnClickListeners
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ApiAddFragment : Fragment(R.layout.fragment_add_api), View.OnClickListener {
    private val binding by FragmentBinding(FragmentAddApiBinding::bind)
    private val viewModel by viewModel<ApiAddViewModel>()
    private val navViewModel by sharedViewModel<NavViewModel>()

    private var dialogAddParam: AlertDialog? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.navViewModel = navViewModel
        setClickListener()
    }

    private fun setClickListener() {
        setOnClickListeners(binding.fabSave, binding.fabAddParam, binding.fabImport)
    }

    //  View.OnClickListener
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.fabSave -> viewModel.onSaveBtnClick()
            R.id.fabAddParam -> {
                viewModel.onAddParamBtnClick()
                showDialogAddParam()
            }
            R.id.fabImport -> viewModel.onImportBtnClick()
            R.id.btnDialogAddParam -> {
                viewModel.onDialogBtnAddParam()
                dialogAddParam?.dismiss()
            }
        }
    }

    // TODO Move logic to ViewModel
    private fun showDialogAddParam() {
        dialogAddParam = MaterialAlertDialogBuilder(requireContext())
            .setView(R.layout.dialog_add_param)
            .show()?.apply {
                // TODO listen key and value adn pass it below
                viewModel.tempKey = ""
                viewModel.tempValue = ""
                findViewById<TextInputLayout>(R.id.tilParamValue)?.editText
                    ?.setOnEditorActionListener { textView, i, keyEvent ->
                        if (i == EditorInfo.IME_ACTION_DONE) {
                            viewModel.onDialogBtnAddParam()
                            dialogAddParam?.dismiss()
                            true
                        } else {
                            false
                        }
                    }
                findViewById<View>(R.id.btnDialogAddParam)?.setOnClickListener(this@ApiAddFragment)
            }
    }
}
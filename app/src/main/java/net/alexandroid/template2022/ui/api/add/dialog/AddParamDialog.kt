package net.alexandroid.template2022.ui.api.add.dialog

import android.content.Context
import android.content.DialogInterface
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import net.alexandroid.template2022.R
import net.alexandroid.template2022.db.model.api.Param

class AddParamDialog(private val callBack: SubmitParamCallBack) : View.OnClickListener,
    TextView.OnEditorActionListener, DialogInterface.OnDismissListener, DefaultLifecycleObserver {

    private var dialog: AlertDialog? = null
    private var tilKey: TextInputLayout? = null
    private var tilValue: TextInputLayout? = null
    private var btnAddParam: View? = null

    fun show(context: Context, param: Param) {
        if (dialog != null) return
        dialog = MaterialAlertDialogBuilder(context)
            .setView(R.layout.dialog_add_param)
            .setOnDismissListener(this)
            .show()?.apply {
                findViews()
                setData(param)
                setDialogListeners()
            }
    }

    private fun AlertDialog.findViews() {
        tilKey = findViewById(R.id.tilParamKey)
        tilValue = findViewById(R.id.tilParamValue)
        btnAddParam = findViewById(R.id.btnDialogAddParam)
    }

    private fun setData(param: Param) {
        tilKey?.editText?.setText(param.key)
        tilValue?.editText?.setText(param.value)
    }

    private fun setDialogListeners() {
        tilValue?.editText?.setOnEditorActionListener(this)
        btnAddParam?.setOnClickListener(this)
    }

    private fun onKeyValueSubmit() {
        val key = tilKey?.editText?.text?.toString() ?: ""
        val value = tilValue?.editText?.text?.toString() ?: ""
        callBack.onSubmitParam(Param(key, value))
        dialog?.dismiss()
    }

    // View.OnClickListener
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnDialogAddParam -> onKeyValueSubmit()
        }
    }

    // TextView.OnEditorActionListener
    override fun onEditorAction(v: TextView?, i: Int, k: KeyEvent?): Boolean {
        return if (i == EditorInfo.IME_ACTION_DONE) {
            onKeyValueSubmit()
            true
        } else {
            false
        }
    }

    // DefaultLifecycleObserver
    override fun onStop(owner: LifecycleOwner) {
        dialog?.dismiss()
    }

    // DialogInterface.OnDismissListener
    override fun onDismiss(d: DialogInterface) {
        clearDialog()
    }

    private fun clearDialog() {
        tilValue?.editText?.setOnEditorActionListener(null)
        btnAddParam?.setOnClickListener(null)
        dialog?.setOnDismissListener(null)
        tilKey = null
        tilValue = null
        btnAddParam = null
        dialog = null
    }

    // CallBack
    interface SubmitParamCallBack {
        fun onSubmitParam(param: Param)
    }
}
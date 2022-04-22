package net.alexandroid.template2022.ui.api.add

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

class AddParamDialog(private val submitCallBack: SubmitCallBack) : View.OnClickListener,
    TextView.OnEditorActionListener, DialogInterface.OnDismissListener, DefaultLifecycleObserver {

    private var dialogAddParam: AlertDialog? = null
    private var tilKey: TextInputLayout? = null
    private var tilValue: TextInputLayout? = null
    private var btnAddParam: View? = null

    fun show(context: Context) {
        if (dialogAddParam != null) return
        dialogAddParam = MaterialAlertDialogBuilder(context)
            .setView(R.layout.dialog_add_param)
            .setOnDismissListener(this)
            .show()?.apply {
                findViews()
                setDialogListeners()
            }
    }

    private fun AlertDialog.findViews() {
        tilKey = findViewById(R.id.tilParamKey)
        tilValue = findViewById(R.id.tilParamValue)
        btnAddParam = findViewById(R.id.btnDialogAddParam)
    }

    private fun setDialogListeners() {
        tilValue?.editText?.setOnEditorActionListener(this@AddParamDialog)
        btnAddParam?.setOnClickListener(this@AddParamDialog)
    }

    private fun onKeyValueSubmit() {
        val key = tilKey?.editText?.text?.toString() ?: ""
        val value = tilValue?.editText?.text?.toString() ?: ""
        submitCallBack.onSubmit(key, value)
        dialogAddParam?.dismiss()
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
        dialogAddParam?.dismiss()
    }

    // DialogInterface.OnDismissListener
    override fun onDismiss(d: DialogInterface) {
        clearDialog()
    }

    private fun clearDialog() {
        tilValue?.editText?.setOnEditorActionListener(null)
        btnAddParam?.setOnClickListener(null)
        dialogAddParam?.setOnDismissListener(null)
        tilKey = null
        tilValue = null
        btnAddParam = null
        dialogAddParam = null
    }

    // CallBack
    interface SubmitCallBack {
        fun onSubmit(key: String, value: String)
    }
}
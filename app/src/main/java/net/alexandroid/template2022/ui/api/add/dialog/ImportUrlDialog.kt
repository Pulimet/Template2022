package net.alexandroid.template2022.ui.api.add.dialog

import android.content.Context
import android.content.DialogInterface
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import net.alexandroid.template2022.R

class ImportUrlDialog(private val callBack: SubmitUrlCallBack) : View.OnClickListener,
    TextView.OnEditorActionListener, DialogInterface.OnDismissListener, DefaultLifecycleObserver,
    TextWatcher {

    private var dialog: AlertDialog? = null
    private var tilUrl: TextInputLayout? = null
    private var btnAddParam: View? = null

    fun show(context: Context) {
        if (dialog != null) return
        dialog = MaterialAlertDialogBuilder(context)
            .setView(R.layout.dialog_import_url)
            .setOnDismissListener(this)
            .show()?.apply {
                findViews()
                setDialogListeners()
            }
    }

    private fun AlertDialog.findViews() {
        tilUrl = findViewById(R.id.tilImportUrl)
        btnAddParam = findViewById(R.id.btnDialogImportUrl)
    }

    private fun setDialogListeners() {
        tilUrl?.editText?.addTextChangedListener(this)
        btnAddParam?.setOnClickListener(this)
    }

    private fun onUrlChanged(url: String) {
        val isValid = Patterns.WEB_URL.matcher(url).matches()
        btnAddParam?.isEnabled = isValid
    }

    private fun onKeyValueSubmit() {
        val url = tilUrl?.editText?.text?.toString() ?: ""
        callBack.onSubmitUrl(url)
        dialog?.dismiss()
    }

    // View.OnClickListener
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnDialogImportUrl -> onKeyValueSubmit()
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
        tilUrl?.editText?.removeTextChangedListener(this)
        btnAddParam?.setOnClickListener(null)
        dialog?.setOnDismissListener(null)
        tilUrl = null
        btnAddParam = null
        dialog = null
    }

    // TextWatcher
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        // Nothing to do
    }

    override fun onTextChanged(url: CharSequence?, p1: Int, p2: Int, p3: Int) {
        onUrlChanged(url.toString())
    }

    override fun afterTextChanged(p0: Editable?) {
        // Nothing to do
    }

    // CallBack
    interface SubmitUrlCallBack {
        fun onSubmitUrl(url: String)
    }
}
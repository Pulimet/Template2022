package net.alexandroid.template2022.utils

import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

// Short way to collect StateFlow on coroutine when Activity Started.
// Useful when you need to collect one StateFlow, but could be used for many.
fun <T> Flow<T>.collectIt(lifecycleOwner: LifecycleOwner, function: (T) -> Unit) {
    lifecycleOwner.lifecycleScope.launch {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            collect {
                function.invoke(it)
            }
        }
    }
}

// Set on click listener
fun View.OnClickListener.setOnClickListeners(vararg views: View) {
    views.forEach { it.setOnClickListener(this) }
}

fun Fragment.showToast(@StringRes resource: Int) {
    showToast(getString(resource))
}

fun Fragment.showToast(message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}
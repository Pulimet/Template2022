package net.alexandroid.template2022.utils

import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// Short way to collect StateFlow on coroutine when Activity Started.
// Useful when you need to collect one StateFlow, but could be used for many.
fun <T> StateFlow<T>.collectIt(lifecycleOwner: LifecycleOwner, function: (T) -> Unit) {
    lifecycleOwner.lifecycleScope.launch {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            collect {
                function.invoke(it)
            }
        }
    }
}

// Launch function on coroutine when Activity STARTED.
// Useful when you need to collect multiple StateFlows.
fun LifecycleOwner.launchOnStarted(function: () -> Unit) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            function.invoke()
        }
    }
}

// Set on click listener
fun View.OnClickListener.setOnClickListeners(vararg views: View) {
    views.forEach { it.setOnClickListener(this) }
}
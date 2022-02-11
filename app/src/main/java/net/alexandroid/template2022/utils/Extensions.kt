package net.alexandroid.template2022.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

fun <T> StateFlow<T>.launchAndCollect(activity: AppCompatActivity, function: (T) -> Unit) {
    activity.lifecycleScope.launch {
        activity.repeatOnLifecycle(Lifecycle.State.STARTED) {
            collect {
                function.invoke(it)
            }
        }
    }
}
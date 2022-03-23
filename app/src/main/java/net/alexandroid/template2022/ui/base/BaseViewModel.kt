package net.alexandroid.template2022.ui.base

import androidx.lifecycle.ViewModel
import net.alexandroid.template2022.utils.logs.logI

abstract class BaseViewModel : ViewModel() {
    init {
        logI(this.javaClass.simpleName)
    }

    override fun onCleared() {
        super.onCleared()
        logI(this.javaClass.simpleName)
    }
}
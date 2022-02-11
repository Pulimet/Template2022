package net.alexandroid.template2022.ui

import net.alexandroid.template2022.ui.base.BaseViewModel
import net.alexandroid.template2022.utils.logD

class NavViewModel : BaseViewModel() {
    fun onActivityOnCreate() {
        logD("${hashCode()}")
    }

    fun onFragmentViewCreated() {
        logD("${hashCode()}")
    }
}
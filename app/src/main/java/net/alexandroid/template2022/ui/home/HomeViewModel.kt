package net.alexandroid.template2022.ui.home

import net.alexandroid.template2022.ui.base.BaseViewModel
import net.alexandroid.template2022.utils.logD

class HomeViewModel : BaseViewModel() {
    fun onFragmentViewCreated() {
        logD("${hashCode()}")
    }
}
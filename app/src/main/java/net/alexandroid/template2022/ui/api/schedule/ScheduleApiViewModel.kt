package net.alexandroid.template2022.ui.api.schedule

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import net.alexandroid.template2022.ui.base.BaseViewModel
import net.alexandroid.template2022.ui.navigation.NavViewModel

class ScheduleApiViewModel : BaseViewModel() {
    lateinit var navViewModel: NavViewModel

    private val _scheduleBtnEnabled = MutableStateFlow(false)
    val scheduleBtnState: StateFlow<Boolean> = _scheduleBtnEnabled

    fun onBtnSetLaunchDateClick() {
        TODO("Not yet implemented")
    }

    fun onBtnSetLaunchTimeClick() {
        TODO("Not yet implemented")
    }

    fun onBtnSetRepeatClick() {
        TODO("Not yet implemented")
    }

    fun onFabScheduleApiClick() {
        TODO("Not yet implemented")
    }
}
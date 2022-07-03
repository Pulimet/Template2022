package net.alexandroid.template2022.ui.api.schedule

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import net.alexandroid.template2022.R
import net.alexandroid.template2022.ui.base.BaseViewModel
import net.alexandroid.template2022.ui.navigation.NavViewModel
import net.alexandroid.template2022.utils.GetResource
import net.alexandroid.template2022.utils.emitSharedFlow

class ScheduleApiViewModel(getResource: GetResource) : BaseViewModel() {
    lateinit var navViewModel: NavViewModel

    private val _scheduleBtnEnabled = MutableStateFlow(false)
    val scheduleBtnState = _scheduleBtnEnabled.asStateFlow()

    private val _notifForAction = MutableStateFlow(false)
    val notifForAction = _notifForAction.asStateFlow()

    private val _notifOnRequest = MutableStateFlow(false)
    val notifOnRequest = _notifOnRequest.asStateFlow()

    private val _notifOnResponse = MutableStateFlow(false)
    val notifOnResponse = _notifOnResponse.asStateFlow()

    private val _launchDateText = MutableStateFlow(getResource.getString(R.string.not_set_date))
    val launchDateText = _launchDateText.asStateFlow()

    private val _launchTimeText = MutableStateFlow(getResource.getString(R.string.not_set_time))
    val launchTimeText = _launchTimeText.asStateFlow()

    private val _repeatText = MutableStateFlow(getResource.getString(R.string.not_set_repeat))
    val repeatText = _repeatText.asStateFlow()

    private val _openDatePicker = MutableSharedFlow<Unit>()
    val openDatePicker = _openDatePicker.asSharedFlow()

    private val _openTimePicker = MutableSharedFlow<Unit>()
    val openTimePicker = _openTimePicker.asSharedFlow()

    private val _openRepeatPicker = MutableSharedFlow<Unit>()
    val openRepeatPicker = _openRepeatPicker.asSharedFlow()

    // User Actions
    fun onBtnSetLaunchDateClick() {
        emitSharedFlow(_openDatePicker)
    }

    fun onBtnSetLaunchTimeClick() {
        emitSharedFlow(_openTimePicker)
    }

    fun onBtnSetRepeatClick() {
        emitSharedFlow(_openRepeatPicker)
    }

    fun onFabScheduleApiClick() {
        TODO("Not yet implemented")
    }

    fun onDateSelected() {
        TODO("Not yet implemented")
    }

    fun onTimeSelected() {
        TODO("Not yet implemented")
    }

    fun onRepeatSelected() {
        TODO("Not yet implemented")
    }
}
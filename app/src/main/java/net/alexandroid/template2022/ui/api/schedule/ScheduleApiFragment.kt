package net.alexandroid.template2022.ui.api.schedule

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import net.alexandroid.template2022.R
import net.alexandroid.template2022.databinding.FragmentScheduleApiBinding
import net.alexandroid.template2022.ui.binding.FragmentBinding
import net.alexandroid.template2022.ui.navigation.NavViewModel
import net.alexandroid.template2022.utils.collectIt
import net.alexandroid.template2022.utils.setOnClickListeners
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ScheduleApiFragment : Fragment(R.layout.fragment_schedule_api), View.OnClickListener {
    private val binding by FragmentBinding(FragmentScheduleApiBinding::bind)
    private val viewModel by viewModel<ScheduleApiViewModel>()
    private val navViewModel by sharedViewModel<NavViewModel>()
    private val args: ScheduleApiFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.navViewModel = navViewModel
        binding.tvUrl.text = args.api?.getUrl() ?: ""
        setClickListener()
        observeViewModel()
    }

    private fun setClickListener() {
        setOnClickListeners(
            binding.btnSetLaunchDate,
            binding.btnSetLaunchTime,
            binding.btnSetRepeat,
            binding.fabScheduleApi
        )
    }

    private fun observeViewModel() {
        viewModel.apply {
            notifForAction.collectIt(viewLifecycleOwner) { binding.switchShowNotifBefore.isChecked = it }
            notifOnRequest.collectIt(viewLifecycleOwner) { binding.switchShowNotifRequest.isChecked = it }
            notifOnResponse.collectIt(viewLifecycleOwner) { binding.switchShowNotifResponse.isChecked = it }
            launchDateText.collectIt(viewLifecycleOwner) { binding.tvLaunchDate.text = it }
            launchTimeText.collectIt(viewLifecycleOwner) { binding.tvLaunchTime.text = it }
            repeatText.collectIt(viewLifecycleOwner) { binding.tvRepeat.text = it }

            scheduleBtnState.collectIt(viewLifecycleOwner) { binding.fabScheduleApi.isEnabled = it }
        }
    }

    //  View.OnClickListener
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnSetLaunchDate -> viewModel.onBtnSetLaunchDateClick()
            R.id.btnSetLaunchTime -> viewModel.onBtnSetLaunchTimeClick()
            R.id.btnSetRepeat -> viewModel.onBtnSetRepeatClick()
            R.id.fabScheduleApi -> viewModel.onFabScheduleApiClick()
        }
    }
}
package net.alexandroid.template2022.ui.api.schedule

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import net.alexandroid.template2022.R
import net.alexandroid.template2022.databinding.FragmentScheduleApiBinding
import net.alexandroid.template2022.ui.binding.FragmentBinding
import net.alexandroid.template2022.ui.navigation.NavViewModel
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
        setClickListener()

        // TODO support scheduling Api call with result as notification
        // TODO support scheduling notification with action to invoke api
    }

    private fun setClickListener() {
        setOnClickListeners(
            binding.tvText
        )
    }

    //  View.OnClickListener
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tvText -> {}
        }
    }
}
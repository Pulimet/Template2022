package net.alexandroid.template2022.ui.api.list.recycler

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import net.alexandroid.template2022.R
import net.alexandroid.template2022.databinding.ItemApiBinding
import net.alexandroid.template2022.db.model.api.Api
import net.alexandroid.template2022.utils.setOnClickListeners

class ApiHolder(v: View, private val listener: OnApiAction) :
    RecyclerView.ViewHolder(v), View.OnClickListener {

    private val binding = ItemApiBinding.bind(v)
    private var api: Api? = null

    init {
        setOnClickListeners(itemView, binding.btnEdit, binding.btnDelete, binding.btnSchedule)
    }

    fun onBindViewHolder(api: Api) {
        this.api = api
        binding.tvUrl.text = api.baseUrl
    }

    // View.OnClickListener
    override fun onClick(v: View?) {
        if (adapterPosition == RecyclerView.NO_POSITION || api == null) return
        api?.let {
            when (v?.id) {
                R.id.btnEdit -> listener.onBtnEditClick(it)
                R.id.btnDelete -> listener.onBtnDeleteClick(it)
                R.id.btnSchedule -> listener.onBtnScheduleClick(it)
                else -> listener.onClick(it)
            }
        }
    }
}
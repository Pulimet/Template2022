package net.alexandroid.template2022.ui.api.list.recycler

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import net.alexandroid.template2022.databinding.ItemApiBinding
import net.alexandroid.template2022.db.model.api.Api

class ApiHolder(v: View, private val listener: OnApiAction) :
    RecyclerView.ViewHolder(v), View.OnClickListener {

    init {
        itemView.setOnClickListener(this)
    }

    private val binding = ItemApiBinding.bind(v)
    private var api: Api? = null

    fun onBindViewHolder(api: Api) {
        this.api = api
        binding.tvUrl.text = api.baseUrl
    }

    // View.OnClickListener
    override fun onClick(v: View?) {
        if (adapterPosition != RecyclerView.NO_POSITION) {
            api?.let { listener.onClick() }
        }
    }
}
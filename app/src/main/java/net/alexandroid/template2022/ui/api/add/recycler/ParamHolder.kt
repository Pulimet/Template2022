package net.alexandroid.template2022.ui.api.add.recycler

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import net.alexandroid.template2022.databinding.ItemParamBinding
import net.alexandroid.template2022.db.model.api.Param

class ParamHolder(v: View, private val listener: OnParamAction) :
    RecyclerView.ViewHolder(v), View.OnClickListener {

    init {
        itemView.setOnClickListener(this)
    }

    private val binding = ItemParamBinding.bind(v)
    private var param: Param? = null

    @SuppressLint("SetTextI18n")
    fun onBindViewHolder(param: Param) {
        this.param = param
        binding.tvKey.text = "${param.key}:"
        binding.tvValue.text = param.value
    }

    // View.OnClickListener
    override fun onClick(v: View?) {
        if (adapterPosition != RecyclerView.NO_POSITION) {
            param?.let { listener.onClick() }
        }
    }
}
package net.alexandroid.template2022.ui.api.add.recycler

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import net.alexandroid.template2022.R
import net.alexandroid.template2022.databinding.ItemParamBinding
import net.alexandroid.template2022.db.model.api.Param

class ParamHolder(v: View, private val listener: OnParamAction) :
    RecyclerView.ViewHolder(v), View.OnClickListener {

    private val binding = ItemParamBinding.bind(v)
    private var param: Param? = null

    init {
        binding.btnEdit.setOnClickListener(this)
        binding.btnDelete.setOnClickListener(this)
    }

    @SuppressLint("SetTextI18n")
    fun onBindViewHolder(param: Param) {
        this.param = param
        binding.tvKey.text = "${param.key}:"
        binding.tvValue.text = param.value
    }

    // View.OnClickListener
    override fun onClick(v: View?) {
        if (adapterPosition != RecyclerView.NO_POSITION && v != null) {
            param?.let {
                handleClick(v.id, it)
            }
        }
    }

    private fun handleClick(id: Int, param: Param) {
        when (id) {
            R.id.btnEdit -> listener.onBtnEditClick(param)
            R.id.btnDelete -> listener.onBtnDeleteClick(param)
        }
    }
}
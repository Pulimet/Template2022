package net.alexandroid.template2022.ui.api.add.recycler

import androidx.recyclerview.widget.DiffUtil

class ParamDiff : DiffUtil.ItemCallback<Param>() {
    override fun areItemsTheSame(oldItem: Param, newItem: Param) = oldItem.key == newItem.key
    override fun areContentsTheSame(oldItem: Param, newItem: Param) = oldItem == newItem
}
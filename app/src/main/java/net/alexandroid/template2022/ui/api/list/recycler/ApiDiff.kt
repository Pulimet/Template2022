package net.alexandroid.template2022.ui.api.list.recycler

import androidx.recyclerview.widget.DiffUtil
import net.alexandroid.template2022.db.model.api.Api

class ApiDiff : DiffUtil.ItemCallback<Api>() {
    override fun areItemsTheSame(oldItem: Api, newItem: Api) = oldItem.baseUrl == newItem.baseUrl
    override fun areContentsTheSame(oldItem: Api, newItem: Api) = oldItem == newItem
}
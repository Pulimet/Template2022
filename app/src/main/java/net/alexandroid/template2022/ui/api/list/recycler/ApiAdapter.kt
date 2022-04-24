package net.alexandroid.template2022.ui.api.list.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import net.alexandroid.template2022.R
import net.alexandroid.template2022.db.model.api.Api

class ApiAdapter(private val listener: OnApiAction) :
    ListAdapter<Api, ApiHolder>(ApiDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApiHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_api, parent, false)
        return ApiHolder(v, listener)
    }

    override fun onBindViewHolder(holder: ApiHolder, position: Int) {
        holder.onBindViewHolder(getItem(position))
    }
}

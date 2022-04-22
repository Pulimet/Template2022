package net.alexandroid.template2022.ui.api.add.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import net.alexandroid.template2022.R

class ParamsAdapter(private val listener: OnParamAction) :
    ListAdapter<Param, ParamHolder>(ParamDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParamHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_param, parent, false)
        return ParamHolder(v, listener)
    }

    override fun onBindViewHolder(holder: ParamHolder, position: Int) {
        holder.onBindViewHolder(getItem(position))
    }
}

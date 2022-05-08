package net.alexandroid.template2022.ui.api.list.recycler

import net.alexandroid.template2022.db.model.api.Api

interface OnApiAction {
    fun onClick(api: Api)
    fun onBtnEditClick(api: Api)
    fun onBtnDeleteClick(api: Api)
}
package net.alexandroid.template2022.ui.api.add.recycler

import net.alexandroid.template2022.db.model.api.Param

interface OnParamAction {
    fun onBtnEditClick(param: Param)
    fun onBtnDeleteClick(param: Param)
}
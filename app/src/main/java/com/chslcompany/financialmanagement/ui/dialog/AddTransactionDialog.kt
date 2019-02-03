package com.chslcompany.financialmanagement.ui.dialog

import android.content.Context
import android.view.ViewGroup
import com.chslcompany.financialmanagement.R
import com.chslcompany.financialmanagement.base.BaseDialog
import com.chslcompany.financialmanagement.model.Type

class AddTransactionDialog(viewGroup: ViewGroup, context: Context) : BaseDialog(viewGroup, context) {

    override val buttonPositiveName: String
        get() = "Adicionar"


    override fun titleBy(type: Type) = if (type == Type.PROFIT) R.string.add_profit
                                       else R.string.add_expense


}
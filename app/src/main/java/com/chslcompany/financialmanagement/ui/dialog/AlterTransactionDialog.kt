package com.chslcompany.financialmanagement.ui.dialog

import android.content.Context
import android.view.ViewGroup
import com.chslcompany.financialmanagement.R
import com.chslcompany.financialmanagement.base.BaseDialog
import com.chslcompany.financialmanagement.model.Transaction
import com.chslcompany.financialmanagement.model.Type
import com.chslcompany.financialmanagement.util.TransactionDelegate
import com.chslcompany.financialmanagement.util.convertToBrazilianFormat

class AlterTransactionDialog(viewGroup: ViewGroup, val context: Context): BaseDialog(viewGroup, context) {

    override val buttonPositiveName: String
        get() = "Alterar"


    override fun titleBy(type: Type) = if (type == Type.PROFIT) R.string.edit_profit
                                       else R.string.edit_expense



    fun initDialog(transaction : Transaction,transactionDelegate: TransactionDelegate){

        val type = transaction.type
        super.initDialog(type,transactionDelegate)

        fieldValue.setText(transaction.value.toString())
        fieldDate.setText(transaction.date.convertToBrazilianFormat())
        val categoryReturned = context.resources.getStringArray(categoryBy(type))
        val categoryPosition = categoryReturned.indexOf(transaction.category)
        fieldCategory.setSelection(categoryPosition,true)

    }






}
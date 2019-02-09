package com.chslcompany.financialmanagement.ui.adapter

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.chslcompany.financialmanagement.R
import com.chslcompany.financialmanagement.model.Type
import com.chslcompany.financialmanagement.model.Transaction
import com.chslcompany.financialmanagement.util.formatToBrazilianCurrency
import com.chslcompany.financialmanagement.util.convertToBrazilianFormat
import com.chslcompany.financialmanagement.util.limitUntil
import kotlinx.android.synthetic.main.item_transaction.view.*

class TransactionListAdapter(private val transactions: List<Transaction>,
                             private val context: Context
) : BaseAdapter() {

    private val categoryLimit = 14

    override fun getItem(posicao: Int) = transactions[posicao]
    override fun getItemId(p0: Int) = 0.toLong()
    override fun getCount() = transactions.size

    override fun getView(posicao: Int, view: View?, parent: ViewGroup?): View {
        val viewCriada = LayoutInflater.from(context)
            .inflate(R.layout.item_transaction, parent, false)

        val transaction = getItem(posicao)

        addValue(transaction, viewCriada)
        addIcon(transaction, viewCriada)
        addCategory(viewCriada, transaction)
        addDate(viewCriada, transaction)

        return viewCriada
    }

    private fun addDate(viewCriada: View, transaction: Transaction) {
        viewCriada.transacao_data.text = transaction.date
            .convertToBrazilianFormat()
    }

    private fun addCategory(viewCriada: View, transaction: Transaction) {
        viewCriada.transacao_categoria.text = transaction.category
            .limitUntil(categoryLimit)
    }

    private fun addIcon(transaction: Transaction, viewCriada: View) {
        val icon = iconBy(transaction.type)
        viewCriada.transacao_icone
            .setBackgroundResource(icon)
    }

    private fun iconBy(type: Type): Int {
        if (type == Type.PROFIT) {
            return R.drawable.icone_transacao_item_receita
        }
        return R.drawable.icone_transacao_item_despesa
    }

    private fun addValue(transaction: Transaction, viewCriada: View) {
        val color: Int = colorBy(transaction.type)
        viewCriada.transacao_valor
            .setTextColor(color)
        viewCriada.transacao_valor.text = transaction.value
            .formatToBrazilianCurrency()
    }

    private fun colorBy(type: Type): Int {
        if (type == Type.PROFIT) {
            return ContextCompat.getColor(context, R.color.receita)
        }
        return ContextCompat.getColor(context, R.color.despesa)
    }



}
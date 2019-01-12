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

class TransactionListAdapter(private val transacoes: List<Transaction>,
                             private val context: Context
) : BaseAdapter() {

    private val limiteDaCategoria = 14

    override fun getItem(posicao: Int) = transacoes[posicao]
    override fun getItemId(p0: Int) = 0.toLong()
    override fun getCount() = transacoes.size

    override fun getView(posicao: Int, view: View?, parent: ViewGroup?): View {
        val viewCriada = LayoutInflater.from(context)
            .inflate(R.layout.item_transaction, parent, false)

        val transacao = getItem(posicao)

        adicionaValor(transacao, viewCriada)
        adicionaIcone(transacao, viewCriada)
        adicionaCategoria(viewCriada, transacao)
        adicionaData(viewCriada, transacao)

        return viewCriada
    }

    private fun adicionaData(viewCriada: View, transacao: Transaction) {
        viewCriada.transacao_data.text = transacao.date
            .convertToBrazilianFormat()
    }

    private fun adicionaCategoria(viewCriada: View, transacao: Transaction) {
        viewCriada.transacao_categoria.text = transacao.category
            .limitUntil(limiteDaCategoria)
    }

    private fun adicionaIcone(transacao: Transaction, viewCriada: View) {
        val icone = iconePor(transacao.type)
        viewCriada.transacao_icone
            .setBackgroundResource(icone)
    }

    private fun iconePor(type: Type): Int {
        if (type == Type.PROFIT) {
            return R.drawable.icone_transacao_item_receita
        }
        return R.drawable.icone_transacao_item_despesa
    }

    private fun adicionaValor(transacao: Transaction, viewCriada: View) {
        val cor: Int = corPor(transacao.type)
        viewCriada.transacao_valor
            .setTextColor(cor)
        viewCriada.transacao_valor.text = transacao.value
            .formatToBrazilianCurrency()
    }

    private fun corPor(type: Type): Int {
        if (type == Type.PROFIT) {
            return ContextCompat.getColor(context, R.color.receita)
        }
        return ContextCompat.getColor(context, R.color.despesa)
    }



}
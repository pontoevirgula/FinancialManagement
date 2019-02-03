package com.chslcompany.financialmanagement.ui.activity

import android.support.v4.content.ContextCompat
import android.view.View
import com.chslcompany.financialmanagement.R
import com.chslcompany.financialmanagement.model.Type
import com.chslcompany.financialmanagement.model.Transaction
import com.chslcompany.financialmanagement.util.formatToBrazilianCurrency
import kotlinx.android.synthetic.main.resumo_card.view.*

class SummaryView(val view : View) {

     fun consumption(transacoes: List<Transaction>) {

         val totalReceita : Double = transacoes
                .filter { transaction -> transaction.type == Type.PROFIT }
                .sumByDouble { transaction -> transaction.value.toDouble()  }
         view.resumo_card_receita.text = totalReceita.toBigDecimal().formatToBrazilianCurrency()

         val totalDespesa : Double = transacoes
                .filter { transaction -> transaction.type == Type.EXPENSE }
                .sumByDouble { transaction -> transaction.value.toDouble()  }
         view.resumo_card_despesa.text = totalDespesa.toBigDecimal().formatToBrazilianCurrency()

         view.resumo_card_total.text = totalReceita.minus(totalDespesa).toBigDecimal().formatToBrazilianCurrency()
         val colorFieldTotal = totalReceita.minus(totalDespesa).toFloat()
         when {
             colorFieldTotal > 0.0 -> view.resumo_card_total.setTextColor(ContextCompat.getColor(view.context, R.color.receita))
             colorFieldTotal < 0.0 -> view.resumo_card_total.setTextColor(ContextCompat.getColor(view.context, R.color.despesa))
             else -> {}
         }
     }

}
package com.chslcompany.financialmanagement.ui.activity

import android.support.v4.content.ContextCompat
import android.view.View
import com.chslcompany.financialmanagement.R
import com.chslcompany.financialmanagement.model.Type
import com.chslcompany.financialmanagement.model.Transaction
import com.chslcompany.financialmanagement.util.formatToBrazilianCurrency
import kotlinx.android.synthetic.main.resumo_card.view.*

class SummaryView(val view : View) {

     fun consumption(transactions: List<Transaction>) {

         val totalProfit : Double = transactions
                .filter { transaction -> transaction.type == Type.PROFIT }
                .sumByDouble { transaction -> transaction.value.toDouble()  }
         view.resumo_card_receita.text = totalProfit.toBigDecimal().formatToBrazilianCurrency()

         val totalExpense : Double = transactions
                .filter { transaction -> transaction.type == Type.EXPENSE }
                .sumByDouble { transaction -> transaction.value.toDouble()  }
         view.resumo_card_despesa.text = totalExpense.toBigDecimal().formatToBrazilianCurrency()

         view.resumo_card_total.text = totalProfit.minus(totalExpense).toBigDecimal().formatToBrazilianCurrency()
         val colorFieldTotal = totalProfit.minus(totalExpense).toFloat()
         when {
             colorFieldTotal > 0.0 -> view.resumo_card_total.setTextColor(ContextCompat.getColor(view.context, R.color.receita))
             colorFieldTotal < 0.0 -> view.resumo_card_total.setTextColor(ContextCompat.getColor(view.context, R.color.despesa))
             else -> {}
         }
     }

}
package com.chslcompany.financialmanagement.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import com.chslcompany.financialmanagement.R
import com.chslcompany.financialmanagement.model.Type
import com.chslcompany.financialmanagement.model.Transaction
import com.chslcompany.financialmanagement.ui.SummaryView
import com.chslcompany.financialmanagement.ui.adapter.TransactionListAdapter
import com.chslcompany.financialmanagement.util.AddTransactionDialog
import com.chslcompany.financialmanagement.util.TransactionDelegate
import kotlinx.android.synthetic.main.activity_lista_transacoes.*
import java.math.BigDecimal
import java.util.*

class TransactionListActivity : AppCompatActivity()
{


    lateinit var view : View

    val transactions: MutableList<Transaction> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes)


        setupFab()

    }

    private fun setupFab() {
        list_transaction_add_profit.setOnClickListener {
            AddTransactionDialog(window.decorView as ViewGroup, this)
                .setupDialog(Type.PROFIT, object : TransactionDelegate {
                    override fun delegate(transaction: Transaction) {
                        updateTransaction(transaction)
                        lista_transacoes_adiciona_menu.close(true)
                    }
                })
        }

        list_transaction_add_expense.setOnClickListener {
            AddTransactionDialog(window.decorView as ViewGroup, this)
                .setupDialog(Type.EXPENSE, object : TransactionDelegate {
                    override fun delegate(transaction: Transaction) {
                        updateTransaction(transaction)
                        lista_transacoes_adiciona_menu.close(true)
                    }
                })
        }
    }


    private fun updateTransaction(transactionCreated: Transaction) {
        transactions.add(transactionCreated)

        setupSummary()
        setupList()
    }

    private fun setupSummary() {
        view = window.decorView
        SummaryView(view).consumption(transactions)
    }


    private fun setupList() {
        lista_transacoes_listview.adapter = TransactionListAdapter(transactions, this)
    }

    private fun TransactionsExamples(): List<Transaction> {
        return listOf(
                Transaction(
                type = Type.EXPENSE,
                category = "almoço de final de semana",
                date = Calendar.getInstance(),
                value = BigDecimal(20.5)
                ),
                Transaction(
                    value = BigDecimal(100.0),
                    type = Type.PROFIT,
                    category = "Economia"
                ),
                Transaction(
                    value = BigDecimal(250.0),
                    type = Type.EXPENSE
                ),
                Transaction(
                    value = BigDecimal(50.0),
                    category = "Prêmio",
                    type = Type.PROFIT
                )
        )
    }

}

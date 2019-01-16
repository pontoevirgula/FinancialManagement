package com.chslcompany.financialmanagement.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import com.chslcompany.financialmanagement.R
import com.chslcompany.financialmanagement.model.Transaction
import com.chslcompany.financialmanagement.model.Type
import com.chslcompany.financialmanagement.ui.adapter.TransactionListAdapter
import com.chslcompany.financialmanagement.ui.dialog.AddTransactionDialog
import com.chslcompany.financialmanagement.util.TransactionDelegate
import kotlinx.android.synthetic.main.activity_lista_transacoes.*

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
        lvTransactionAddProfit.setOnClickListener {
            callDialogAddTransaction(Type.PROFIT)
        }

        lvTransactionAddExpensive.setOnClickListener {
            callDialogAddTransaction(Type.EXPENSE)
        }
    }

    private fun callDialogAddTransaction(type: Type){
        AddTransactionDialog(window.decorView as ViewGroup, this)
            .setupDialog(type, object : TransactionDelegate {
                override fun delegate(transaction: Transaction) {
                    updateTransaction(transaction)
                    lvTransactionsAddMenu.close(true)
                }
            })
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
        lvTransactions.adapter = TransactionListAdapter(transactions, this)
        lvTransactions.setOnItemClickListener { parent, view, position, id ->
            var transactionClicked = transactions[position]
        }
    }



}

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
import com.chslcompany.financialmanagement.ui.dialog.AlterTransactionDialog
import com.chslcompany.financialmanagement.util.TransactionDelegate
import kotlinx.android.synthetic.main.activity_lista_transacoes.*

class TransactionListActivity : AppCompatActivity() {

    private lateinit var activityView: View
    val transactions: MutableList<Transaction> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes)

        activityView = window.decorView
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

    private fun callDialogAddTransaction(type: Type) {
        AddTransactionDialog(activityView as ViewGroup, this)
            .initDialog(type, object : TransactionDelegate {
                override fun delegate(transaction: Transaction) {
                    transactions.add(transaction)
                    updateTransactionList()
                    lvTransactionsAddMenu.close(true)
                }
            })
    }


    private fun updateTransactionList() {
        setupSummary()
        setupList()
    }

    private fun setupSummary() = SummaryView(activityView).consumption(transactions)


    private fun setupList() {
        with(lvTransactions) {
            adapter = TransactionListAdapter(transactions, this@TransactionListActivity)
            setOnItemClickListener { _, _, position, _ ->
                val transactionClicked = transactions[position]
                callAlterDialog(transactionClicked, position)
            }
        }
    }

    private fun callAlterDialog(transactionClicked: Transaction, position: Int) {
        AlterTransactionDialog(activityView as ViewGroup, this)
            .initDialog(transactionClicked, object : TransactionDelegate {
                override fun delegate(transaction: Transaction) {
                    transactions.set(position, transaction)
                    updateTransactionList()
                }
            })
    }


}

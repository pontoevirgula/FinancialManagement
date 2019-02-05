package com.chslcompany.financialmanagement.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.AdapterView
import com.chslcompany.financialmanagement.R
import com.chslcompany.financialmanagement.model.Transaction
import com.chslcompany.financialmanagement.model.Type
import com.chslcompany.financialmanagement.ui.adapter.TransactionListAdapter
import com.chslcompany.financialmanagement.ui.dialog.AddTransactionDialog
import com.chslcompany.financialmanagement.ui.dialog.AlterTransactionDialog
import com.chslcompany.financialmanagement.util.TransactionDelegate
import kotlinx.android.synthetic.main.activity_lista_transacoes.*

class TransactionListActivity : AppCompatActivity() {

    private val activityView by lazy {
        window.decorView
    }
    private val viewGroupActivity by lazy {
        activityView as ViewGroup
    }
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

    private fun callDialogAddTransaction(type: Type) {
        AddTransactionDialog(viewGroupActivity, this)
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
            setOnCreateContextMenuListener { menu, v, menuInfo ->
                menu.add(Menu.NONE,1,Menu.NONE,"Remover")
            }
        }
    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId
        if (id == 1){
            val adapterMenuInfo = item.menuInfo as AdapterView.AdapterContextMenuInfo
            val positionMenu = adapterMenuInfo.position
            transactions.removeAt(positionMenu)
            updateTransactionList()
        }
        return super.onContextItemSelected(item)
    }

    private fun callAlterDialog(transactionClicked: Transaction, position: Int) {
        AlterTransactionDialog(viewGroupActivity, this)
            .initDialog(transactionClicked, object : TransactionDelegate {
                override fun delegate(transaction: Transaction) {
                    transactions.set(position, transaction)
                    updateTransactionList()
                }
            })
    }


}

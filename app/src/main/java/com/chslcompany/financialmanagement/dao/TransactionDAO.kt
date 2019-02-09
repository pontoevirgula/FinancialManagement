package com.chslcompany.financialmanagement.dao

import com.chslcompany.financialmanagement.model.Transaction

class TransactionDAO {

    val transactions : List<Transaction> = Companion.transactions

    companion object {
        private val transactions: MutableList<Transaction> = mutableListOf()
    }


    fun add(transaction: Transaction) = Companion.transactions.add(transaction)

    fun alter(transaction: Transaction, position : Int) { Companion.transactions[position] = transaction }

    fun remove(position: Int) = Companion.transactions.removeAt(position)


}
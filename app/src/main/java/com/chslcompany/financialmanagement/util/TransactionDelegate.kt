package com.chslcompany.financialmanagement.util

import com.chslcompany.financialmanagement.model.Transaction

interface TransactionDelegate {

    fun delegate(transaction: Transaction)
}
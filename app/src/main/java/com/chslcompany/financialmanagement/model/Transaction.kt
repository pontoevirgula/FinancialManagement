package com.chslcompany.financialmanagement.model

import java.math.BigDecimal
import java.util.*

data class Transaction (
    val value : BigDecimal,
    val category : String = "Indefinida",
    val type : Type,
    val date : Calendar = Calendar.getInstance()
                        )
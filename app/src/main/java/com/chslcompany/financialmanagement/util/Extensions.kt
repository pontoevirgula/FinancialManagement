package com.chslcompany.financialmanagement.util

import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*




fun Calendar.convertToBrazilianFormat() : String{
    val formatoBrasileiro = "dd/MM/yyyy"
    val format = SimpleDateFormat(formatoBrasileiro)
    return format.format(this.time)
}


fun String.limitUntil(caracteres: Int) : String{
    if(this.length > caracteres){
        val primeiroCaracter = 0
        return "${this.substring(primeiroCaracter, caracteres)}..."
    }
    return this
}



fun BigDecimal.formatToBrazilianCurrency() : String {
    val brazilianFormat = DecimalFormat
        .getCurrencyInstance(Locale("pt", "br"))
    return brazilianFormat
        .format(this)
        .replace("R$", "R$ ")
        .replace("-R$ ","R$ -")
}

fun String.convertDateFromStringToCalendar(): Calendar {
    val formatBrazilianDate = SimpleDateFormat("dd/mm/yyyy")
    val convertedDate: Date = formatBrazilianDate.parse(this)
    val date = Calendar.getInstance()
    date.time = convertedDate
    return date
}
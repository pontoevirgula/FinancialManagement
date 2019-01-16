package com.chslcompany.financialmanagement.ui.dialog

import android.app.DatePickerDialog
import android.content.Context
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.chslcompany.financialmanagement.R
import com.chslcompany.financialmanagement.model.Type
import com.chslcompany.financialmanagement.model.Transaction
import com.chslcompany.financialmanagement.util.TransactionDelegate
import com.chslcompany.financialmanagement.util.convertDateFromStringToCalendar
import com.chslcompany.financialmanagement.util.convertToBrazilianFormat
import kotlinx.android.synthetic.main.activity_lista_transacoes.view.*
import kotlinx.android.synthetic.main.form_transaction.view.*
import java.math.BigDecimal
import java.util.*

class AlterTransactionDialog(val viewGroup: ViewGroup, val context: Context) {

    private val createdView = createLayout()

    fun setupDialog(type : Type,transactionDelegate: TransactionDelegate){

        setupFieldDate()
        setupFieldCategory(type)

        //CRIAÇÃO DO DIALOG
        setupForm(type,transactionDelegate)

    }


    //TODO --REFATORAR CRIANDO UMA CUSTOMDIALOG TENDO COMO REFERENCIA O MATERIALDIALOG
    private fun setupForm(type: Type, transactionDelegate: TransactionDelegate) {

        val title = if(type == Type.PROFIT) R.string.add_profit
                    else  R.string.add_expense

        AlertDialog.Builder(context)
            .setTitle(title)
            .setView(createdView)
            .setPositiveButton("Adicionar") { _, _ ->
                val consumptionValue = createdView.et_transaction_value.text.toString()
                val value = convertStringToBigDecimal(consumptionValue)

                val consumptionDate = createdView.et_transaction_date.text.toString()
                val date = consumptionDate.convertDateFromStringToCalendar()

                val consumptionCategory = createdView.spn_transaction_category.selectedItem.toString()


                val transactionCreated = Transaction(
                    value = value,
                    type = type,
                    date = date,
                    category = consumptionCategory
                )

                transactionDelegate.delegate(transactionCreated)


            }
            .setNegativeButton("Cancelar", null)
            .show()
    }



    private fun convertStringToBigDecimal(consumptionValue: String): BigDecimal {
        val value = try {
            BigDecimal(consumptionValue)
        } catch (e: NumberFormatException) {
            val snackbar = Snackbar.make(
                createdView.clTransaction,
                "Falha ao inserir valor",
                Snackbar.LENGTH_SHORT
            )
            snackbar.show()
            BigDecimal.ZERO
        }
        return value
    }


    private fun setupFieldCategory(type: Type) {

        val categories = if(type == Type.PROFIT) R.array.categorias_de_receita
                         else  R.array.categorias_de_despesa

        val adapter = ArrayAdapter.
                        createFromResource(
                            context,
                            categories,
                            android.R.layout.simple_spinner_dropdown_item)

        createdView.spn_transaction_category.adapter = adapter

    }

    private fun createLayout() : View {

        return LayoutInflater.from(context)
                .inflate(
                    R.layout.form_transaction,
                    viewGroup,
                    false
                )

    }

    private fun setupFieldDate() {

        val today = Calendar.getInstance()
        val year = today.get(Calendar.YEAR)
        val month = today.get(Calendar.MONTH)
        val day = today.get(Calendar.DAY_OF_MONTH)


        createdView.et_transaction_date.setText(today.convertToBrazilianFormat())
        createdView.et_transaction_date.setOnClickListener {
            DatePickerDialog(context, DatePickerDialog.OnDateSetListener { _, ano, mes, dia ->

                val selectedDate = Calendar.getInstance()
                selectedDate.set(ano, mes, dia)
                createdView.et_transaction_date
                    .setText(selectedDate.convertToBrazilianFormat())
            }
                , year,month,day)
                .show()
        }
    }

}
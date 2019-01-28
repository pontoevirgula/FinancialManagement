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

    private val editedView = createLayout()
    val fieldValue = editedView.et_transaction_value
    val fieldDate = editedView.et_transaction_date
    val fieldCategory = editedView.spn_transaction_category


    fun initDialog(transaction : Transaction,transactionDelegate: TransactionDelegate){

        val type = transaction.type

        setupFieldDate()
        setupFieldCategory(type)
        editForm(type,transactionDelegate)

        fieldValue.setText(transaction.value.toString())
        fieldDate.setText(transaction.date.convertToBrazilianFormat())
        val categoryReturned = context.resources.getStringArray(categoryBy(type))
        val categoryPosition = categoryReturned.indexOf(transaction.category)
        fieldCategory.setSelection(categoryPosition,true)

    }


    //TODO --REFATORAR CRIANDO UMA CUSTOMDIALOG TENDO COMO REFERENCIA O MATERIALDIALOG
    private fun editForm( type: Type, transactionDelegate: TransactionDelegate) {

        val title = if(type == Type.PROFIT) R.string.edit_profit
                    else  R.string.edit_expense

        AlertDialog.Builder(context)
            .setTitle(title)
            .setView(editedView)
            .setPositiveButton("Salvar edições") { _, _ ->
                val consumptionValue = editedView.et_transaction_value.text.toString()
                val value = convertStringToBigDecimal(consumptionValue)

                val consumptionDate = editedView.et_transaction_date.text.toString()
                val date = consumptionDate.convertDateFromStringToCalendar()

                val consumptionCategory = editedView.spn_transaction_category.selectedItem.toString()


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
                editedView.clTransaction,
                "Falha ao inserir valor",
                Snackbar.LENGTH_SHORT
            )
            snackbar.show()
            BigDecimal.ZERO
        }
        return value
    }


    private fun setupFieldCategory(type: Type) {

        val categories = categoryBy(type)

        val adapter = ArrayAdapter.
                        createFromResource(
                            context,
                            categories,
                            android.R.layout.simple_spinner_dropdown_item)

        editedView.spn_transaction_category.adapter = adapter

    }

    private fun categoryBy(type: Type): Int {
        val categories = if (type == Type.PROFIT) R.array.categorias_de_receita
                         else R.array.categorias_de_despesa

        return categories
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


        editedView.et_transaction_date.setText(today.convertToBrazilianFormat())

        editedView.et_transaction_date.setOnClickListener {
            DatePickerDialog(context, DatePickerDialog.OnDateSetListener { _, ano, mes, dia ->

                val selectedDate = Calendar.getInstance()
                selectedDate.set(ano, mes, dia)
                editedView.et_transaction_date
                    .setText(selectedDate.convertToBrazilianFormat())
            }
                , year,month,day)
                .show()
        }
    }

}
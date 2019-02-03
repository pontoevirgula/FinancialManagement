package com.chslcompany.financialmanagement.base

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.chslcompany.financialmanagement.R
import com.chslcompany.financialmanagement.model.Transaction
import com.chslcompany.financialmanagement.model.Type
import com.chslcompany.financialmanagement.util.TransactionDelegate
import com.chslcompany.financialmanagement.util.convertDateFromStringToCalendar
import com.chslcompany.financialmanagement.util.convertToBrazilianFormat
import kotlinx.android.synthetic.main.activity_lista_transacoes.view.*
import kotlinx.android.synthetic.main.form_transaction.view.*
import java.math.BigDecimal
import java.util.*

abstract class BaseDialog(private val viewGroup: ViewGroup, private val context: Context) {

    protected  val view = createLayout()
    protected val fieldValue = view.et_transaction_value
    protected val fieldDate = view.et_transaction_date
    protected val fieldCategory = view.spn_transaction_category
    protected abstract val buttonPositiveName : String


    fun initDialog(type : Type, transactionDelegate: TransactionDelegate){
        setupFieldDate()
        setupFieldCategory(type)
        setupForm(type,transactionDelegate)
    }

    //TODO --REFATORAR CRIANDO UMA CUSTOMDIALOG TENDO COMO REFERENCIA O MATERIALDIALOG
    private fun setupForm(type: Type, transactionDelegate: TransactionDelegate) {

        val title = titleBy(type)


        AlertDialog.Builder(context)
            .setTitle(title)
            .setView(view)
            .setPositiveButton(buttonPositiveName) { _, _ ->
                val consumptionValue = view.et_transaction_value.text.toString()
                val value = convertStringToBigDecimal(consumptionValue)

                val consumptionDate = view.et_transaction_date.text.toString()
                val date = consumptionDate.convertDateFromStringToCalendar()

                val consumptionCategory = view.spn_transaction_category.selectedItem.toString()


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

    protected abstract fun titleBy(type: Type): Int

    private fun convertStringToBigDecimal(consumptionValue: String): BigDecimal {
        val value = try {
            BigDecimal(consumptionValue)
        } catch (e: NumberFormatException) {
            val snackbar = Snackbar.make(
                view.clTransaction,
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

        view.spn_transaction_category.adapter = adapter

    }

    protected fun categoryBy(type: Type) =
                            if (type == Type.PROFIT) R.array.categorias_de_receita
                            else R.array.categorias_de_despesa

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


        view.et_transaction_date.setText(today.convertToBrazilianFormat())
        view.et_transaction_date.setOnClickListener {
            DatePickerDialog(context, DatePickerDialog.OnDateSetListener { _, ano, mes, dia ->

                val selectedDate = Calendar.getInstance()
                selectedDate.set(ano, mes, dia)
                view.et_transaction_date
                    .setText(selectedDate.convertToBrazilianFormat())
            }
                , year,month,day)
                .show()
        }
    }
}
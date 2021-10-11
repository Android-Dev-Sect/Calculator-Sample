package com.kre4.calculator.middleware

import android.app.Activity
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.kre4.calculator.CalculationResult
import com.kre4.calculator.R
import java.lang.Exception

class UserTextHandlerImp(private val layout: View): UserTextHandler {
    private val expressionEditText: EditText = (layout.context as Activity).findViewById(R.id.working_space)
    private val resultTextView: TextView =  (layout.context as Activity).findViewById(R.id.result_space)

    override fun handleInputNewChar(char: Char){
        showChar(char)
        clearResult()

    }
    override fun handleInputDeleteChar(){
        deleteChar()
        clearResult()
    }
    override fun handleInputDeleteAll(){
        expressionEditText.setText("")
    }
    override fun getExpression(): String{
        return expressionEditText.text.toString()
    }

    override fun getResult(): String {
        return resultTextView.text.toString()
    }

    override fun showAnswer(answer: String){
        resultTextView.text = answer
    }

    override fun handleCalculationResult(calculationResult: CalculationResult) {
        when (calculationResult){
            CalculationResult.Companion.ExpressionError -> resultTextView.text = layout.context.getString(R.string.expression_error)
            CalculationResult.Companion.OperatorsError -> resultTextView.text = layout.context.getString(R.string.lot_of_operators)
            CalculationResult.Companion.ProhibitedActions -> resultTextView.text = layout.context.getString(R.string.prohibited_actions)
            CalculationResult.Companion.ZeroDivision -> resultTextView.text = layout.context.getString(R.string.zero_division)
            is CalculationResult.Companion.SuccessfulCalculation -> showAnswer(calculationResult.answer)
            else -> {}
        }
    }

    private fun showChar(char: Char) {

        val currSelection = expressionEditText.selectionStart
        expressionEditText.setText(
            expressionEditText.text.toString().addCharAtIndex(
                char,
                expressionEditText.selectionStart
            )
        )
        expressionEditText.setSelection(currSelection + 1)

    }

    private fun deleteChar() {
        try {
            val currSelection = expressionEditText.selectionStart
            expressionEditText.setText(
                expressionEditText.text.toString()
                    .removeCharAtIndex(expressionEditText.selectionStart - 1)
            )
            expressionEditText.setSelection(currSelection - 1)
        } catch (e: Exception) {
        }
    }

    private fun clearResult(){
        resultTextView.text = ""
    }

    private fun String.addCharAtIndex(char: Char, index: Int) =
        StringBuilder(this).insert(index, char).toString()

    private fun String.removeCharAtIndex(index: Int) =
        StringBuilder(this).delete(index, index + 1)
}
package com.kre4.calculator.middleware

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.kre4.calculator.ErrorEnum
import com.kre4.calculator.R
import java.lang.Exception
import kotlin.math.exp

class UserTextHandlerImp(private val Layout: View): UserTextHandler {
    private val expressionEditText: EditText = (Layout.context as Activity).findViewById(R.id.working_space)
    private val resultTextView: TextView =  (Layout.context as Activity).findViewById(R.id.result_space)

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

    override fun handleInputWithErrorsAndShowResult(error: ErrorEnum) {
        when (error){
            ErrorEnum.EXPRESSION_ERROR -> resultTextView.text = Layout.context.getString(R.string.expression_error)
            ErrorEnum.OPERATORS_ERROR -> resultTextView.text = Layout.context.getString(R.string.lot_of_operators)
            ErrorEnum.PROHIBITED_ACTIONS -> resultTextView.text = Layout.context.getString(R.string.prohibited_actions)
            ErrorEnum.ZERO_DIVISION -> resultTextView.text = Layout.context.getString(R.string.zero_division)

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
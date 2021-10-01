package com.kre4.calculator.middleware

import android.content.ClipData
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.kre4.calculator.DIALOG_TAG
import com.kre4.calculator.ErrorEnum

import com.kre4.calculator.hard_logic.handlers.Calculator
import com.kre4.calculator.hard_logic.handlers.Tokenizer
import com.kre4.calculator.hard_logic.history.HistroryStorage
import com.kre4.calculator.list.HistoryListItem
import java.lang.ArithmeticException
import java.lang.Exception
import java.lang.NumberFormatException
import java.util.*

class KeyboardCallbackImp(private val userTextHandler: UserTextHandler,
                            private val dialogFragment: DialogFragment,
                          private val calculator : Calculator,
                          private val tokenizer: Tokenizer,
                          private val historyStorage: HistroryStorage
                          ): KeyboardCallback {

    override fun onExpressionChanged(char: Char) {
        userTextHandler.handleInputNewChar(char)

    }

    override fun onExpressionBackspaced() {
        userTextHandler.handleInputDeleteChar()
    }

    override fun onExpressionCleared(){
        userTextHandler.handleInputDeleteAll()
    }

    override fun onExpressionRequestCalculation() {
        userTextHandler.handleInputWithErrorsAndShowResult(getAnswer())
        historyStorage.saveExpression(
            HistoryListItem(
                userTextHandler.getExpression(),
                userTextHandler.getResult()
            )
        )
    }



    override fun onHistoryRequested(v: View) {
        val manager: FragmentManager =
            (v.context as AppCompatActivity).supportFragmentManager
        dialogFragment.show(manager, DIALOG_TAG)

    }

    private fun getAnswer(): ErrorEnum {
        var tokenSubsequence: Array<Tokenizer.Token>? = null
        var resultToken: Tokenizer.Token.VAL? = null
        try {
            tokenSubsequence = tokenizer.tokenize(
                userTextHandler.getExpression()
            )

        } catch (e: Exception) {
            return ErrorEnum.EXPRESSION_ERROR
        }

        tokenSubsequence ?: run {
            return ErrorEnum.EXPRESSION_ERROR
        }
        try {
            resultToken = calculator.calculate(tokenSubsequence)
            return if (resultToken != null) {
                userTextHandler.showAnswer(resultToken.value.toString())
                ErrorEnum.NO_ERROR
            } else
                ErrorEnum.EXPRESSION_ERROR

        } catch (e: EmptyStackException) {
            return ErrorEnum.OPERATORS_ERROR
        } catch (e: NumberFormatException) {
            return ErrorEnum.PROHIBITED_ACTIONS
        } catch (e: ArithmeticException) {
            return ErrorEnum.ZERO_DIVISION
        } catch (e: Exception) {
            return ErrorEnum.EXPRESSION_ERROR
        }
    }
}
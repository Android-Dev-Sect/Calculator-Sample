package com.kre4.calculator.middleware

import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.kre4.calculator.DIALOG_TAG
import com.kre4.calculator.CalculationResult

import com.kre4.calculator.hard_logic.handlers.Calculator
import com.kre4.calculator.hard_logic.handlers.Tokenizer
import com.kre4.calculator.hard_logic.history.HistoryStorage
import com.kre4.calculator.list.HistoryListItem
import java.lang.ArithmeticException
import java.lang.Exception
import java.lang.NumberFormatException
import java.util.*
import java.util.concurrent.Executors

class KeyboardCallbackImp(
    private val userTextHandler: UserTextHandler,
    private val dialogFragment: DialogFragment,
    private val calculator: Calculator,
    private val tokenizer: Tokenizer,
    private val historyStorage: HistoryStorage
) : KeyboardCallback {

    override fun onExpressionChanged(char: Char) {
        userTextHandler.handleInputNewChar(char)

    }

    override fun onExpressionBackspaced() {
        userTextHandler.handleInputDeleteChar()
    }

    override fun onExpressionCleared() {
        userTextHandler.handleInputDeleteAll()
    }

    override fun onExpressionRequestCalculation() {
        val executor = Executors.newSingleThreadExecutor()
        executor.execute {
            val answer = getAnswer()
            userTextHandler.handleCalculationResult(answer)
            if (answer.isSuccessful) {
                historyStorage.saveExpression(
                    HistoryListItem(
                        userTextHandler.getExpression(),
                        userTextHandler.getResult()
                    )
                )
            }
        }

    }


    override fun onHistoryRequested(v: View) {
        val manager: FragmentManager =
            (v.context as AppCompatActivity).supportFragmentManager
        dialogFragment.show(manager, DIALOG_TAG)

    }

    private fun getAnswer(): CalculationResult {
        var tokenSubsequence: Array<Tokenizer.Token>? = null
        var resultToken: Tokenizer.Token.VAL? = null
        try {
            tokenSubsequence = tokenizer.tokenize(
                userTextHandler.getExpression()
            )

        } catch (e: Exception) {
            return CalculationResult.Companion.ExpressionError
        }

        tokenSubsequence ?: run {
            return CalculationResult.Companion.ExpressionError
        }
        try {
            resultToken = calculator.calculate(tokenSubsequence)
            return if (resultToken != null) {
                // TODO: check this comment and delete(may be)
                //
                CalculationResult.Companion.SuccessfulCalculation(resultToken.value.toString())
            } else
                CalculationResult.Companion.ExpressionError

        } catch (e: EmptyStackException) {
            return CalculationResult.Companion.OperatorsError
        } catch (e: NumberFormatException) {
            return CalculationResult.Companion.ProhibitedActions
        } catch (e: ArithmeticException) {
            return CalculationResult.Companion.ZeroDivision
        } catch (e: Exception) {
            return CalculationResult.Companion.ExpressionError
        }
    }
}
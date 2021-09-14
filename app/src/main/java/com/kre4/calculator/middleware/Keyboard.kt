package com.kre4.calculator.middleware

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.kre4.calculator.*
import com.kre4.calculator.hard_logic.handlers.Calculator
import com.kre4.calculator.hard_logic.handlers.Tokenizer
import com.kre4.calculator.hard_logic.history.HistroryStorage
import com.kre4.calculator.list.Item
import java.lang.ArithmeticException
import java.lang.Exception
import java.lang.NumberFormatException
import java.util.*

class Keyboard(private val tokenizer: Tokenizer,
               private val calculator: Calculator,
               private val historyStorage: HistroryStorage,
               private val dialogFragment: DialogFragment) :
    View.OnClickListener {


    fun load(context: Context) {
        (context as Activity).findViewById<EditText>(R.id.working_space).showSoftInputOnFocus =
            false

        setListener(context, R.id.history)
        setListener(context, R.id.open_bracket)
        setListener(context, R.id.close_bracket)
        setListener(context, R.id.clear_all)
        setListener(context, R.id.backspace)
        setListener(context, R.id.seven)
        setListener(context, R.id.eight)
        setListener(context, R.id.nine)
        setListener(context, R.id.divide)
        setListener(context, R.id.multiply)
        setListener(context, R.id.four)
        setListener(context, R.id.five)
        setListener(context, R.id.six)
        setListener(context, R.id.plus)
        setListener(context, R.id.minus)
        setListener(context, R.id.one)
        setListener(context, R.id.two)
        setListener(context, R.id.three)
        setListener(context, R.id.sqrt)
        setListener(context, R.id.zero)
        setListener(context, R.id.dot)
        setListener(context, R.id.equal)

    }

    private fun setListener(context: Context, id: Int) {
        (context as Activity).findViewById<View>(id).setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        v ?: return
        var needToClear = true
        when (v.id) {
            R.id.history -> {
                needToClear = false
                val manager: FragmentManager =
                    (v.context as AppCompatActivity).supportFragmentManager
                dialogFragment.show(manager, DIALOG_TAG)
                //dlg.showOnTheScreen()


            }
            R.id.clear_all -> (v.context as Activity).findViewById<EditText>(R.id.working_space)
                .setText("")
            R.id.backspace -> deleteChar(v.context)
            R.id.open_bracket -> showChar(v.context, obracket)
            R.id.close_bracket -> showChar(v.context, cbracket)
            R.id.nine -> showChar(v.context, '9')
            R.id.eight -> showChar(v.context, '8')
            R.id.seven -> showChar(v.context, '7')
            R.id.six -> showChar(v.context, '6')
            R.id.five -> showChar(v.context, '5')
            R.id.four -> showChar(v.context, '4')
            R.id.three -> showChar(v.context, '3')
            R.id.two -> showChar(v.context, '2')
            R.id.one -> showChar(v.context, '1')
            R.id.zero -> showChar(v.context, '0')
            R.id.dot -> showChar(v.context, '.')
            R.id.divide -> showChar(v.context, divide)
            R.id.multiply -> showChar(v.context, multiply)
            R.id.plus -> showChar(v.context, plus)
            R.id.minus -> showChar(v.context, minus)
            R.id.sqrt -> showChar(v.context, sqrt)
            R.id.equal -> {
                if (getAnswer(v)) {
                    historyStorage.saveExpression(
                        Item(
                            (v.context as Activity).findViewById<EditText>(
                                R.id.working_space
                            ).text.toString(),
                            (v.context as Activity).findViewById<TextView>(R.id.result_space).text.toString()
                        )
                    )
                }
                needToClear = false
            }
            else -> return
        }
        if (needToClear)
            (v.context as Activity).findViewById<TextView>(R.id.result_space).text = ""
    }

    private fun getAnswer(v: View): Boolean {
        var isSuccess = false
        var answer = v.context.getString(R.string.default_error)
        var tokenSubsequence: Array<Tokenizer.Token>? = null
        var resultToken: Tokenizer.Token.VAL? = null
        try {
            tokenSubsequence = tokenizer.tokenize(
                (v.context as Activity).findViewById<EditText>(R.id.working_space).text.toString()
            )

        } catch (e: Exception) {
            answer = v.context.getString(R.string.expression_error)
        }

        tokenSubsequence ?: run {
            ((v.context as Activity).findViewById<TextView>(R.id.result_space)).text =
                v.context.getString(R.string.expression_error)
            return isSuccess
        }
        try {
            resultToken = calculator.calculate(tokenSubsequence)
            if (resultToken != null) {
                answer = resultToken.value.toString()
                isSuccess = true
            }

        } catch (e: EmptyStackException) {
            answer = v.context.getString(R.string.lot_of_operators)
        } catch (e: NumberFormatException) {
            answer = v.context.getString(R.string.prohibited_actions)
        } catch (e: ArithmeticException) {
            answer = v.context.getString(R.string.zero_division)
        } catch (e: Exception) {
            answer = v.context.getString(R.string.expression_error)
        }

        ((v.context as Activity).findViewById<TextView>(R.id.result_space)).text = answer
        return isSuccess

    }


    private fun showChar(context: Context, char: Char) {
        val workingSpace = (context as Activity).findViewById<EditText>(R.id.working_space)
        val currSelection = workingSpace.selectionStart
        workingSpace.setText(
            workingSpace.text.toString().addCharAtIndex(
                char,
                workingSpace.selectionStart
            )
        )
        workingSpace.setSelection(currSelection + 1)

    }

    private fun deleteChar(context: Context) {
        try {
            val workingSpace =
                (context as Activity).findViewById<EditText>(R.id.working_space)
            val currSelection = workingSpace.selectionStart
            workingSpace.setText(
                workingSpace.text.toString()
                    .removeCharAtIndex(workingSpace.selectionStart - 1)
            )
            workingSpace.setSelection(currSelection - 1)
        } catch (e: Exception) {
        }
    }

    private fun String.addCharAtIndex(char: Char, index: Int) =
        StringBuilder(this).insert(index, char).toString()

    private fun String.removeCharAtIndex(index: Int) =
        StringBuilder(this).delete(index, index + 1)
}
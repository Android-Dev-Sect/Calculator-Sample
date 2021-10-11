package com.kre4.calculator.middleware

import android.app.Activity
import android.view.View
import com.kre4.calculator.*

/*
 private val tokenizer: Tokenizer,
               private val calculator: Calculator,
               private val historyStorage: HistroryStorage,
               private val dialogFragment: DialogFragment,
               */
class Keyboard( private val keyboardCallback: KeyboardCallback,
                private val keyboardLayout: View) :
    View.OnClickListener {

    fun load() {
        //(context as Activity).findViewById<EditText>(R.id.working_space).showSoftInputOnFocus =
        //    false

        setListener( R.id.history)
        setListener( R.id.open_bracket)
        setListener( R.id.close_bracket)
        setListener( R.id.clear_all)
        setListener( R.id.backspace)
        setListener( R.id.seven)
        setListener( R.id.eight)
        setListener( R.id.nine)
        setListener( R.id.divide)
        setListener( R.id.multiply)
        setListener( R.id.four)
        setListener( R.id.five)
        setListener( R.id.six)
        setListener( R.id.plus)
        setListener( R.id.minus)
        setListener( R.id.one)
        setListener( R.id.two)
        setListener(R.id.three)
        setListener(R.id.sqrt)
        setListener(R.id.zero)
        setListener(R.id.dot)
        setListener(R.id.equal)

    }

    private fun setListener(id: Int) {
        (keyboardLayout.context as Activity).findViewById<View>(id).setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        v ?: return
        when (v.id) {
            R.id.history -> keyboardCallback.onHistoryRequested(v)
            R.id.clear_all -> keyboardCallback.onExpressionCleared()
            R.id.backspace -> keyboardCallback.onExpressionBackspaced()
            R.id.open_bracket -> keyboardCallback.onExpressionChanged(obracket)
            R.id.close_bracket ->  keyboardCallback.onExpressionChanged(cbracket)
            R.id.nine ->  keyboardCallback.onExpressionChanged('9')
            R.id.eight ->  keyboardCallback.onExpressionChanged( '8')
            R.id.seven ->  keyboardCallback.onExpressionChanged( '7')
            R.id.six ->  keyboardCallback.onExpressionChanged( '6')
            R.id.five ->  keyboardCallback.onExpressionChanged('5')
            R.id.four ->  keyboardCallback.onExpressionChanged( '4')
            R.id.three ->  keyboardCallback.onExpressionChanged( '3')
            R.id.two ->  keyboardCallback.onExpressionChanged( '2')
            R.id.one ->  keyboardCallback.onExpressionChanged('1')
            R.id.zero ->  keyboardCallback.onExpressionChanged( '0')
            R.id.dot ->  keyboardCallback.onExpressionChanged( '.')
            R.id.divide ->  keyboardCallback.onExpressionChanged( divide)
            R.id.multiply ->  keyboardCallback.onExpressionChanged( multiply)
            R.id.plus ->  keyboardCallback.onExpressionChanged( plus)
            R.id.minus ->  keyboardCallback.onExpressionChanged( minus)
            R.id.sqrt ->  keyboardCallback.onExpressionChanged( sqrt)
            R.id.equal -> {
                /*if (getAnswer(v)) {
                    historyStorage.saveExpression(
                        HistoryListItem(
                            (v.context as Activity).findViewById<EditText>(
                                R.id.working_space
                            ).text.toString(),
                            (v.context as Activity).findViewById<TextView>(R.id.result_space).text.toString()
                        )
                    )
                }*/
                keyboardCallback.onExpressionRequestCalculation()

            }
            else -> return
        }

    }





}
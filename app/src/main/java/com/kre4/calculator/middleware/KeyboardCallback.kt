package com.kre4.calculator.middleware

import android.view.View
import android.widget.EditText
import android.widget.TextView

interface KeyboardCallback {
    fun onExpressionChanged(char: Char)
    fun onExpressionBackspaced()
    fun onExpressionCleared()
    fun onExpressionRequestCalculation()
    fun onHistoryRequested(v: View)
}
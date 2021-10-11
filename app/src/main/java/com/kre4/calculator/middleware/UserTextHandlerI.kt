package com.kre4.calculator.middleware

import com.kre4.calculator.CalculationResult

interface UserTextHandler {
    fun handleInputNewChar(char: Char)
    fun getExpression(): String
    fun getResult(): String
    fun showAnswer(answer: String)
    fun handleCalculationResult(error: CalculationResult)
    fun handleInputDeleteChar()
    fun handleInputDeleteAll()
}
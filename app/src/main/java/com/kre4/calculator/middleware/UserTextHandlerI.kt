package com.kre4.calculator.middleware

import com.kre4.calculator.ErrorEnum

interface UserTextHandler {
    fun handleInputNewChar(char: Char)
    fun getExpression(): String
    fun getResult(): String
    fun showAnswer(answer: String)
    fun handleInputWithErrorsAndShowResult(error: ErrorEnum)
    fun handleInputDeleteChar()
    fun handleInputDeleteAll()
}
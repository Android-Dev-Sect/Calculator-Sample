package com.kre4.calculator

/*
    TODO: delete shit
    enum class CalculationResult {
    EXPRESSION_ERROR,
    OPERATORS_ERROR,
    PROHIBITED_ACTIONS,
    ZERO_DIVISION,
    NO_ERROR

} */
sealed class CalculationResult {

    val isSuccessful: Boolean
        get() = this is SuccessfulCalculation

    companion object {
        class SuccessfulCalculation(val answer: String): CalculationResult()
        object ExpressionError: CalculationResult()
        object OperatorsError: CalculationResult()
        object ProhibitedActions: CalculationResult()
        object ZeroDivision: CalculationResult()
    }

}
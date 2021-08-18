package com.kre4.calculator

import android.app.Activity
import android.widget.EditText
import android.widget.TextView
import java.lang.ArithmeticException
import java.lang.Exception
import java.lang.Math.sqrt
import java.lang.NumberFormatException

import java.math.BigDecimal
import java.util.*

// symbol # used for unary minus!
val allowedOperations = listOf('+', '-', '/', '×', '√', '#')

fun convertToPolishNotation(activity: Activity): StringBuilder? {
    val workingSpace: EditText = activity.findViewById(R.id.working_space)
    val stack: Stack<Char> = Stack()
    var polishNotation: StringBuilder = StringBuilder()

    for ((index, char) in workingSpace.text.withIndex()) {
        when (char) {
            in '0'..'9', '.' -> polishNotation.append(char)
            '#' -> return null
            in allowedOperations -> {
                var operator = char
                polishNotation.append(" ")
                // checking for unary minus
                if (operator == '-') {
                    if (index == 0)
                        operator = '#'
                    else if (workingSpace.text[index - 1] in allowedOperations)
                        operator = '#'
                }
                if (!stack.empty() && (getPriority(operator) < getPriority(stack.peek()))) {
                    polishNotation.append(stack.pop())
                    polishNotation.append(" ")

                }
                stack.push(operator)

            }
            '(' -> stack.push(char)
            ')' -> {
                while (!stack.empty()) {
                    if (stack.peek() == '(') {
                        stack.pop()
                        break
                    }
                    polishNotation.append(" ")
                    polishNotation.append(stack.pop())
                }
            }
            else -> return null
        }

    }
    while (!stack.empty()) {
        if (stack.peek() in allowedOperations) {
            polishNotation.append(" ")
            polishNotation.append(stack.pop())
        } else return null
    }


    return if (stack.empty()) {

        StringBuilder(polishNotation.toString().replace("  ", " ")).append(" ")
    } else null

}


fun showResult(activity: Activity) {
    var result: String = activity.getString(R.string.default_error)
    try {
        result = calculatePolishNotation(
            convertToPolishNotation(activity)
        ).toString()
        if (result == "null")
            result = activity.getString(R.string.default_error)

    } catch (e: EmptyStackException) {
        result = activity.getString(R.string.lot_of_operators)
    } catch (e: NumberFormatException) {
        result = activity.getString(R.string.prohibited_actions)
    } catch (e: ArithmeticException) {
        result = "Деление на ноль"
    } catch (e: Exception){}

    (activity.findViewById<TextView>(R.id.result_space)).text = result
}


fun getPriority(char: Char): Short {
    when (char) {
        '#' -> return 4
        '√' -> return 3
        '×', '/' -> return 2
        '+', '-' -> return 1
    }
    return -1
}

fun calculatePolishNotation(polishNotation: StringBuilder?): BigDecimal? {
    if (polishNotation == null)
        return null
    val stack: Stack<BigDecimal> = Stack()

    while (polishNotation.isNotEmpty()) {
        var spaceIndex = polishNotation.indexOf(" ")
        var current: String = polishNotation.substring(0, spaceIndex)
        if (current.isNotEmpty())
            when (current[0]) {
                in '0'..'9' ->
                    stack.push(current.toBigDecimal())
                '√' -> stack.push(sqrtBig(stack.pop()))
                '#' -> stack.push(-stack.pop())
                '+' -> stack.push(stack.pop() + stack.pop())
                '-' -> stack.push(-stack.pop() + stack.pop())
                '×' -> stack.push(stack.pop() * stack.pop())
                '/' -> stack.push(1.toBigDecimal() / (stack.pop() / stack.pop()))

            }
        polishNotation.deleteRange(0, spaceIndex + 1)
    }
    if (stack.size > 1)
        return null
    return stack.pop()
}

fun sqrtBig(A: BigDecimal, SCALE: Int = 8): BigDecimal? {
    var x0 = BigDecimal("0")
    var x1 = BigDecimal(kotlin.math.sqrt(A.toDouble()))
    while (x0 != x1) {
        x0 = x1
        x1 = A.divide(x0, SCALE, BigDecimal.ROUND_HALF_UP)
        x1 = x1.add(x0)
        x1 = x1.divide(2.toBigDecimal(), SCALE, BigDecimal.ROUND_HALF_UP)
    }
    return x1
}
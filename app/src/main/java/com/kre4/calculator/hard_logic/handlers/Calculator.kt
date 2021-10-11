package com.kre4.calculator.hard_logic.handlers


import java.util.*
import kotlin.ArithmeticException
import kotlin.collections.ArrayList
import kotlin.math.sqrt

class Calculator {
    fun calculate(tokenArray: Array<Tokenizer.Token>): Tokenizer.Token.VAL? {
        return calculatePolishNotation(toPolishNotation(tokenArray))
    }

    private fun calculatePolishNotation(tokenArray: Array<Tokenizer.Token>): Tokenizer.Token.VAL? {
        val stack: Stack<Tokenizer.Token.VAL> = Stack()
        for (token in tokenArray) {
            when (token) {
                is Tokenizer.Token.VAL -> stack.push(token)
                Tokenizer.Token.UMINUS -> stack.push(Tokenizer.Token.VAL(-stack.pop().value))
                Tokenizer.Token.SQRT -> {
                    var a = stack.pop().value
                    if (a < 0)
                        throw NumberFormatException()
                    stack.push(Tokenizer.Token.VAL(sqrt(stack.pop().value)))
                }
                Tokenizer.Token.MULTIPLY -> stack.push(Tokenizer.Token.VAL(stack.pop().value * stack.pop().value))
                Tokenizer.Token.DIVIDE -> {
                    var a = stack.pop().value
                    if (a == 0.0)
                        throw ArithmeticException()
                    stack.push(
                        Tokenizer.Token.VAL(stack.pop().value / a)
                    )

                }
                Tokenizer.Token.MINUS -> stack.push(Tokenizer.Token.VAL(-stack.pop().value + stack.pop().value))
                Tokenizer.Token.PLUS -> stack.push(Tokenizer.Token.VAL(stack.pop().value + stack.pop().value))

            }
        }
        return if (stack.size > 1) null
        else stack.pop()
    }

    private fun toPolishNotation(tokenArray: Array<Tokenizer.Token>): Array<Tokenizer.Token> {
        val stack: Stack<Tokenizer.Token> = Stack()
        val polishNotation: MutableList<Tokenizer.Token> = ArrayList()

        for (token in tokenArray) {
            when (token) {
                Tokenizer.Token.PLUS, Tokenizer.Token.MINUS, Tokenizer.Token.SQRT, Tokenizer.Token.UMINUS, Tokenizer.Token.DIVIDE, Tokenizer.Token.MULTIPLY
                -> {
                    if (!stack.empty() && (token.getPriority() < stack.peek().getPriority()))
                        polishNotation.add(stack.pop())
                    stack.push(token)

                }
                is Tokenizer.Token.VAL -> polishNotation.add(token)
                Tokenizer.Token.OBRACKET -> stack.push(token)
                Tokenizer.Token.CBRACKET -> {
                    while (!stack.empty()) {
                        if (stack.peek() == Tokenizer.Token.OBRACKET) {
                            stack.pop()
                            break
                        }
                        polishNotation.add(stack.pop())
                    }
                }
            }
        }
        while (!stack.empty())
            polishNotation.add(stack.pop())
        return polishNotation.toTypedArray()

    }
}
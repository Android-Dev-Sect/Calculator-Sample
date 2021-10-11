package com.kre4.calculator.hard_logic.handlers

import com.kre4.calculator.*

class Tokenizer {
    sealed class Token() {
        class VAL(val value: Double) : Token()
        object PLUS : Token()
        object MINUS : Token()
        object DIVIDE : Token()
        object MULTIPLY : Token()
        object SQRT : Token()
        object UMINUS : Token()      //unary minus
        object OBRACKET : Token()    //open bracket (
        object CBRACKET : Token()     // close bracket )

        fun getPriority(): Short {
            when (this) {
                UMINUS -> return 4
                SQRT -> return 3
                DIVIDE, MULTIPLY -> return 2
                PLUS, MINUS -> return 1
            }
            return -1
        }

    }

    fun tokenize(str: String?): Array<Token>? {
        str ?: return emptyArray()
        val tokenArray: MutableList<Token> = ArrayList()
        var digit = StringBuilder("")
        for ((index, char) in str.withIndex()) {
            if (char in '0'..'9' || char == '.')
                digit.append(char)
            else {
                if (digit.isNotEmpty()) {
                    tokenArray.add(Token.VAL(digit.toString().toDouble()))
                    digit.clear()
                }
                when (char) {
                    minus -> {
                        if (index == 0)
                            tokenArray.add(Token.UMINUS)
                        else if (!(str[index - 1] in '0'..'9' || str[index - 1] == '.'))
                            tokenArray.add(Token.UMINUS)
                        else tokenArray.add(Token.MINUS)
                    }
                    plus -> tokenArray.add(Token.PLUS)
                    divide -> tokenArray.add(Token.DIVIDE)
                    multiply -> tokenArray.add(Token.MULTIPLY)
                    sqrt -> tokenArray.add(Token.SQRT)
                    obracket -> tokenArray.add(Token.OBRACKET)
                    cbracket -> tokenArray.add(Token.CBRACKET)
                    else -> return null

                }
            }

        }
        if (digit.isNotEmpty())
            tokenArray.add(Token.VAL(digit.toString().toDouble()))
        return tokenArray.toTypedArray()
    }
}
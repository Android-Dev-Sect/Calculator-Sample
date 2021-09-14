package com.kre4.calculator.list

data class Item(var expression: String, var result: String) {

    fun toSting(): String {
        return "$expression=$result"
    }
}
// Correct string example "23+42=65"
fun String?.toItem():Item{
    if (this == null) return Item("","")
    return Item(substring(0,indexOf('=')),substring(indexOf('=') + 1,length))
}
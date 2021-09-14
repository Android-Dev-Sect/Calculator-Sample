package com.kre4.calculator.hard_logic.history

import com.kre4.calculator.list.Item

interface HistroryStorage {
    fun saveExpression(item: Item)
    fun getExpressions(): List<Item>
    fun clearStorage()

}
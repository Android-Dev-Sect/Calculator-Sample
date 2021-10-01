package com.kre4.calculator.hard_logic.history

import com.kre4.calculator.list.HistoryListItem

interface HistroryStorage {
    fun saveExpression(historyListItem: HistoryListItem)
    fun getExpressions(): List<HistoryListItem>
    fun clearStorage()

}
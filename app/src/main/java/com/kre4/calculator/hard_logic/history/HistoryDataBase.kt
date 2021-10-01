package com.kre4.calculator.hard_logic.history

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.DatabaseUtils
import android.database.sqlite.SQLiteDatabase
import com.kre4.calculator.*
import com.kre4.calculator.list.HistoryListItem

class HistoryDataBase(private val context: Context) : HistroryStorage {
    private val dbHelper: DataBaseHelper = DataBaseHelper(context)

    override fun saveExpression(historyListItem: HistoryListItem) {

        val contentValues: ContentValues = ContentValues().apply {
            put(DATA_BASE_EXPRESSION, historyListItem.expression)
            put(DATA_BASE_RESULT, historyListItem.result)
        }
        val dataBase: SQLiteDatabase = dbHelper.writableDatabase.apply {
            insert(DATA_BASE_NAME, null, contentValues)
        }

        if (DatabaseUtils.queryNumEntries(dataBase, DATA_BASE_NAME) > shown_amount_of_expressions_in_history_list)
            deleteFirstRow(dataBase)
        dataBase.close()
    }

    override fun getExpressions(): List<HistoryListItem> {
        val listOfHistoryListItems: MutableList<HistoryListItem> = mutableListOf()
        val dataBase: SQLiteDatabase = dbHelper.writableDatabase
        val cursor: Cursor = getCursor(dataBase)
        if (!cursor.moveToFirst()) {
            dataBase.close()
            return returnEmptyList()
        }
        val firstColumnIndex = cursor.getColumnIndex(DATA_BASE_EXPRESSION)
        val secondColumnIndex = cursor.getColumnIndex(DATA_BASE_RESULT)
        do {
            listOfHistoryListItems.add(0,
                HistoryListItem(
                    cursor.getString(firstColumnIndex),
                    cursor.getString(secondColumnIndex)
                )
            )
        } while (cursor.moveToNext())

        dataBase.close()
        return listOfHistoryListItems.toList()
    }

    override fun clearStorage() {
        val dataBase: SQLiteDatabase = dbHelper.writableDatabase
        dataBase.execSQL("DELETE FROM $DATA_BASE_NAME")
        dataBase.close()
    }

    private fun returnEmptyList(): List<HistoryListItem> {

        return listOf(HistoryListItem(context.getString(R.string.empty_results), ""))
    }

    private fun deleteFirstRow(database: SQLiteDatabase) {

        //Delete from artists where rowid IN (Select rowid from artists limit 1);
        database.execSQL("DELETE FROM $DATA_BASE_NAME WHERE rowid IN (SELECT rowid FROM $DATA_BASE_NAME LIMIT 1)");

    }


    private fun getCursor(database: SQLiteDatabase): Cursor {
        return database.query(DATA_BASE_NAME, null, null, null, null, null, null)
    }
}

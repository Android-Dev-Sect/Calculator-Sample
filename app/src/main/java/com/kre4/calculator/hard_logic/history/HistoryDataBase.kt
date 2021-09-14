package com.kre4.calculator.hard_logic.history

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.DatabaseUtils
import android.database.sqlite.SQLiteDatabase
import android.widget.EditText
import com.kre4.calculator.R
import com.kre4.calculator.list.Item

class HistoryDataBase(private val context: Context) : HistroryStorage {
    private val dbHelper: DataBaseHelper = DataBaseHelper(context)

    override fun saveExpression(item: Item) {
        val dataBase: SQLiteDatabase = dbHelper.writableDatabase
        val contentValues: ContentValues = ContentValues()
        contentValues.put(DataBaseHelper.EXPRESSION, item.expression)
        contentValues.put(DataBaseHelper.RESULT, item.result)
        dataBase.insert(DataBaseHelper.DATA_BASE_NAME, null, contentValues)
        if (DatabaseUtils.queryNumEntries(dataBase, DataBaseHelper.DATA_BASE_NAME) > 30)
            deleteFirstRow(dataBase)
        dataBase.close()
    }

    override fun getExpressions(): List<Item> {
        val listOfItems: MutableList<Item> = mutableListOf()
        val dataBase: SQLiteDatabase = dbHelper.writableDatabase
        val cursor: Cursor = getCursor(dataBase)
        if (!cursor.moveToFirst()) {
            dataBase.close()
            return returnEmptyList()
        }
        val firstColumnIndex = cursor.getColumnIndex(DataBaseHelper.EXPRESSION)
        val secondColumnIndex = cursor.getColumnIndex(DataBaseHelper.RESULT)
        do {
            listOfItems.add(0,
                Item(
                    cursor.getString(firstColumnIndex),
                    cursor.getString(secondColumnIndex)
                )
            )
        } while (cursor.moveToNext())

        dataBase.close()
        return listOfItems.toList()
    }

    override fun clearStorage() {
        val dataBase: SQLiteDatabase = dbHelper.writableDatabase
        dataBase.execSQL("delete from ${DataBaseHelper.DATA_BASE_NAME}")
        dataBase.close()
    }

    private fun returnEmptyList(): List<Item> {

        return listOf(Item(context.getString(R.string.empty_results), ""))
    }

    private fun deleteFirstRow(database: SQLiteDatabase) {

        //Delete from artists where rowid IN (Select rowid from artists limit 1);
        database.execSQL("delete from ${DataBaseHelper.DATA_BASE_NAME} where rowid IN (select rowid from ${DataBaseHelper.DATA_BASE_NAME} limit 1)");

    }


    private fun getCursor(database: SQLiteDatabase): Cursor {
        return database.query(DataBaseHelper.DATA_BASE_NAME, null, null, null, null, null, null)
    }
}

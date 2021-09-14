package com.kre4.calculator.hard_logic.history

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DataBaseHelper(context: Context): SQLiteOpenHelper(context, DATA_BASE_NAME, null, DATA_BASE_VERSION ) {
    companion object{
        const val EXPRESSION = "EXPRESSION"
        const val RESULT = "RESULT"
        const val DATA_BASE_NAME = "HistoryDB"
        const val DATA_BASE_VERSION = 1
    }
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table $DATA_BASE_NAME ("
                + "$EXPRESSION text,"
                + "$RESULT text" + ");")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}
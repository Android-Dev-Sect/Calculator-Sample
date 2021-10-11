package com.kre4.calculator.hard_logic.history

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.kre4.calculator.DATA_BASE_EXPRESSION
import com.kre4.calculator.DATA_BASE_NAME
import com.kre4.calculator.DATA_BASE_RESULT
import com.kre4.calculator.DATA_BASE_VERSION


class DataBaseHelper(context: Context): SQLiteOpenHelper(context, DATA_BASE_NAME, null, DATA_BASE_VERSION ) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE $DATA_BASE_NAME ("
                + "$DATA_BASE_EXPRESSION TEXT,"
                + "$DATA_BASE_RESULT TEXT" + ");")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) = Unit
}
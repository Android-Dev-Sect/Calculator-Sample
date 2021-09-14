package com.kre4.calculator

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kre4.calculator.hard_logic.handlers.Calculator
import com.kre4.calculator.hard_logic.handlers.Tokenizer
import com.kre4.calculator.hard_logic.history.HistoryDataBase
import com.kre4.calculator.list.CustomRecyclerAdapter
import com.kre4.calculator.list.Item
import java.lang.Exception
import com.kre4.calculator.middleware.Keyboard

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tokenizer = Tokenizer()
        val calculator = Calculator()
        val historyStorage = HistoryDataBase(this)
        val dialogFragment = HistoryFragment(historyStorage)
        val keyboard = Keyboard(tokenizer, calculator, historyStorage, dialogFragment)
        keyboard.load(this)


    }



}
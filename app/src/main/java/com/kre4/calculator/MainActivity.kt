package com.kre4.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.kre4.calculator.hard_logic.handlers.Calculator
import com.kre4.calculator.hard_logic.handlers.Tokenizer
import com.kre4.calculator.hard_logic.history.HistoryDataBase
import com.kre4.calculator.middleware.Keyboard
import com.kre4.calculator.middleware.KeyboardCallback
import com.kre4.calculator.middleware.KeyboardCallbackImp
import com.kre4.calculator.middleware.UserTextHandlerImp

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tokenizer = Tokenizer()
        val calculator = Calculator()
        val historyStorage = HistoryDataBase(this)
        val dialogFragment = HistoryFragment(historyStorage)
        val factory = LayoutInflater.from(this)
        val myView: View = factory.inflate(R.layout.activity_main, null)
        val userTextHandler = UserTextHandlerImp(myView)
        val keyboardCallback: KeyboardCallback = KeyboardCallbackImp(userTextHandler, dialogFragment, calculator, tokenizer, historyStorage)
        val keyboard = Keyboard(keyboardCallback, myView)
        keyboard.load()

    }
}
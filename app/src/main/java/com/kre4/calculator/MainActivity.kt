package com.kre4.calculator

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import java.lang.Exception


class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Keyboard(this)

    }

    override fun onStart() {
        super.onStart()
        Keyboard(this)
    }

}
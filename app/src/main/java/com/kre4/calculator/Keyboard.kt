package com.kre4.calculator

import android.app.Activity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import java.lang.Exception

class Keyboard(val activity: Activity) {

    private val workingSpace: EditText = activity.findViewById(R.id.working_space)

    init {
        setClickListeners()
        workingSpace.showSoftInputOnFocus = false
    }

    private fun setClickListeners(): Unit {
        val layout: LinearLayout = activity.findViewById(R.id.mainLayout)
        for (i in 2 until layout.childCount) {
            val element: View = layout.getChildAt(i)
            if (element is LinearLayout) {
                handleLinearLayoutButtons(element)
            }
        }
    }

    private fun handleLinearLayoutButtons(layout: LinearLayout): Unit {
        val n = layout.childCount
        for (i in 0 until layout.childCount) {
            val element: View = layout.getChildAt(i)
            if (element is Button) {
                print(element.text)
                element.setOnClickListener(object : View.OnClickListener {
                    override fun onClick(v: View?) {
                        when (element.text) {
                            activity.getString(R.string.history) -> {
                                return//DO IT
                            }
                            "C" -> workingSpace.setText("")
                            "⌫" -> try {
                                val currSelection = workingSpace.selectionStart
                                workingSpace.setText(
                                    workingSpace.text.toString()
                                        .removeCharAtIndex(workingSpace.selectionStart - 1)
                                )
                                workingSpace.setSelection(currSelection - 1)
                            } catch (e: Exception) {
                            }
                            "=" -> {
                                showResult(activity)
                            } //DO iT
                            else -> {
                                val currSelection = workingSpace.selectionStart
                                workingSpace.setText(
                                    workingSpace.text.toString().addCharAtIndex(
                                        element.text[0],
                                        workingSpace.selectionStart
                                    )
                                )
                                workingSpace.setSelection(currSelection + 1)

                            }
                        }
                    }

                })
            }
        }
    }

    private fun String.addCharAtIndex(char: Char, index: Int) =
        StringBuilder(this).apply { insert(index, char) }.toString()

    private fun String.removeCharAtIndex(index: Int) =
        StringBuilder(this).apply { delete(index, index + 1) }


}
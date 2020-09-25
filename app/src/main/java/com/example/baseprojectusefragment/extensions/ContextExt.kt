package com.example.baseprojectusefragment.extensions

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

fun Activity.hideSoftKeyboard() {
    val inputMethodManager: InputMethodManager = getSystemService(
        Activity.INPUT_METHOD_SERVICE
    ) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(
        currentFocus?.windowToken, 0
    )
}

fun Context.showKeyboard(target: EditText) {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(target, InputMethodManager.SHOW_IMPLICIT)
}
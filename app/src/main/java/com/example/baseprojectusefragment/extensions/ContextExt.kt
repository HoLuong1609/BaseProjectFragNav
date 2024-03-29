package com.example.baseprojectusefragment.extensions

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.example.baseprojectusefragment.BaseApplication

val appContext = BaseApplication.instance

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

fun Context.retrievingResources(resourceName: String, resourceType: String): Int {
    return resources.getIdentifier(resourceName, resourceType, packageName);
}

fun Context.dpToDx(dp: Float) = dp * (resources
    .displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
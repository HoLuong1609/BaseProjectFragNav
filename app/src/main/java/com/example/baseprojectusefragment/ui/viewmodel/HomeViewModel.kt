package com.example.baseprojectusefragment.ui.viewmodel

import android.app.Application
import android.content.res.Resources
import android.util.Log
import android.widget.TextView
import com.example.baseprojectusefragment.R
import com.example.baseprojectusefragment.common.Utils.Companion.splitWordsIntoStringsThatFit
import com.example.baseprojectusefragment.ui.base.BaseViewModel
import com.example.baseprojectusefragment.ui.base.SingleLiveEvent

class HomeViewModel(application: Application): BaseViewModel(application) {
    val isEnabled: SingleLiveEvent<Boolean> = SingleLiveEvent<Boolean>().apply { value = false}

    val onGlobalLayoutListener = fun(lineCount: Int) {
        Log.e("LuongHH", "Width: $lineCount")
    }

    fun visibleShowMore(textView: TextView) : Boolean {
        val screenWidth = Resources.getSystem().displayMetrics.widthPixels
        val padding = textView.context.resources.getDimensionPixelOffset(R.dimen._15sdp).toFloat()
        val maxWidth = screenWidth - 2 * padding

        val strings = splitWordsIntoStringsThatFit(
            textView.text.toString(),
            maxWidth,
            textView.paint
        )
        return strings.size > 3
    }
}
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
    val isEnabled: SingleLiveEvent<Boolean> = SingleLiveEvent<Boolean>().apply { value = true}

    val onGlobalLayoutListener = fun(lineCount: Int) {
        Log.e("LuongHH", "Width: $lineCount")
    }

    fun visibleShowMore(textView: TextView) : Boolean {
//        val text = "Example to show restored state. Example to show restored state. Example to show restored state. Example to show restored state. Example to show restored state. Example to show restored state. Example to show restored state"
//        val params = TextMeasurementUtils.TextMeasurementParams.Builder
//            .from(textView).build()
//        val lines = TextMeasurementUtils.getTextLines(text, params)
//        return lines.size > 3
//        val text =
//            "Hello this is a very long and meanless chunk: abcdefghijkonetuhosnahrc.pgraoneuhnotehurc.pgansohtunsaohtu. Hope you like it!"
//        val paint = Paint()
//        paint.textSize = 30f
//        paint.typeface = Typeface.DEFAULT_BOLD

        val screenWidth = Resources.getSystem().displayMetrics.widthPixels
        val padding = context.resources.getDimensionPixelOffset(R.dimen._15sdp).toFloat()
        val maxWidth = screenWidth - 2 * padding

        val strings = splitWordsIntoStringsThatFit(
            textView.text.toString(),
            maxWidth,
            textView.paint
        )
        Log.e("LuongHH", strings.size.toString() + " - " + maxWidth + " - " + padding)
        return strings.size > 3
    }
}
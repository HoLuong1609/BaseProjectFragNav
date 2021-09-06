package com.example.baseprojectusefragment.ui.binding

import android.R
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.baseprojectusefragment.extensions.retrievingResources
import com.example.baseprojectusefragment.ui.widget.TextMeasurementUtils
import com.example.baseprojectusefragment.ui.widget.TextMeasurementUtils.TextMeasurementParams


@Suppress("UNCHECKED_CAST")
@BindingAdapter(value = ["listData"])
fun <T> bindDataRecyclerView(recyclerView: RecyclerView, data: T) {
    recyclerView.adapter?.run {
        if (this is BindDataAdapter<*>) {
            (this as BindDataAdapter<T>).setData(data)
        }
    }
}

@BindingAdapter("app:goneUnless")
fun goneUnless(view: View, visible: Boolean) {
    view.visibility = if (visible) View.VISIBLE else View.GONE
}

@BindingAdapter("app:goneUnlessNote")
fun goneUnlessNote(view: View, textView: TextView) {
    textView.post {
        val params = TextMeasurementParams.Builder
            .from(textView).build()
        val lines = TextMeasurementUtils.getTextLines(textView.text, params)
        val visible = lines.size > 3
        view.visibility = if (visible) View.VISIBLE else View.GONE
    }
}

@BindingAdapter("app:photoUri")
fun photoUri(imageView: ImageView, photoRes: Int) {
    imageView.setImageResource(photoRes)
}

@BindingAdapter("app:font")
fun setFont(textView: TextView, fontName: String) {
    textView.typeface = ResourcesCompat.getFont(
        textView.context, textView.context.retrievingResources(
            fontName,
            "font"
        )
    )
}

@BindingAdapter("app:onGlobalLayoutListener")
fun onGlobalLayoutListener(textView: TextView, listener: (lineCount: Int) -> Unit) {
//    textView.viewTreeObserver
//        .addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
//            override fun onPreDraw(): Boolean {
//                Log.e("LuongHH", textView.lineCount.toString() + " - " + textView.text)
//                listener(textView.lineCount)
//                textView.viewTreeObserver.removeOnPreDrawListener(this)
//                return true
//            }
//        })
    textView.viewTreeObserver.addOnGlobalLayoutListener(object :
        OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            if (textView.lineCount > 0) {
                textView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                Log.e("LuongHH", textView.lineCount.toString() + " - " + textView.text)

                listener(textView.width)
            }
        }
    })
//    textView.postDelayed({
//        Log.e("LuongHH", textView.lineCount.toString() + " - " + textView.text)
//    }, 1000)

}
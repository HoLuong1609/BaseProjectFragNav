package com.example.baseprojectusefragment.ui.binding

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.baseprojectusefragment.extensions.retrievingResources

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

@BindingAdapter("app:photoUri")
fun photoUri(imageView: ImageView, photoRes: Int) {
    imageView.setImageResource(photoRes)
}

@BindingAdapter("app:font")
fun setFont(textView: TextView, fontName: String) {
    textView.typeface = ResourcesCompat.getFont(textView.context, textView.context.retrievingResources(fontName, "font"))
}
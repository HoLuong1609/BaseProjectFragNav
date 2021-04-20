package com.example.baseprojectusefragment.ui.binding

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

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
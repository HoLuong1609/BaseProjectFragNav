package com.example.baseprojectusefragment.ui.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.baseprojectusefragment.extensions.load

@Suppress("UNCHECKED_CAST")
@BindingAdapter(value = ["listData"])
fun <T> bindDataRecyclerView(recyclerView: RecyclerView, data: T) {
    recyclerView.adapter?.run {
        if (this is BindDataAdapter<*>) {
            (this as BindDataAdapter<T>).setData(data)
        }
    }
}

@BindingAdapter("loadImage")
fun loadImage(imageView: ImageView, url: String) {
    imageView.load(url)
}

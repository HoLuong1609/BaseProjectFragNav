package com.example.baseprojectusefragment.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

fun ImageView.load(url: Any) {
    Glide.with(context)
        .asBitmap().load(url).centerCrop()
        .override(width, height).diskCacheStrategy(
            DiskCacheStrategy.ALL
        )
        .into(this)
}

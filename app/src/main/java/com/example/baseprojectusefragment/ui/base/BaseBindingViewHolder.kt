package com.example.baseprojectusefragment.ui.base

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseBindingViewHolder<T>(binding: ViewDataBinding): RecyclerView.ViewHolder(binding.root) {
    abstract fun bind(item: T)
}
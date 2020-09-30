package com.example.baseprojectusefragment.ui.base

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.baseprojectusefragment.ui.binding.BindDataAdapter

abstract class BaseBindingAdapter<M,H: BaseBindingViewHolder<M>>(diffCallback: DiffUtil.ItemCallback<M>): ListAdapter<M, H>(diffCallback),
    BindDataAdapter<List<M>>
package com.example.baseprojectusefragment.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import com.example.baseprojectusefragment.BR
import com.example.baseprojectusefragment.R
import com.example.baseprojectusefragment.data.response.Crypto
import com.example.baseprojectusefragment.databinding.ItemCryptoBinding
import com.example.baseprojectusefragment.ui.base.BaseBindingAdapter
import com.example.baseprojectusefragment.ui.base.BaseBindingViewHolder

class CryptoAdapter(
    var parentLifecycleOwner: LifecycleOwner
) :
    BaseBindingAdapter<Crypto, CryptoAdapter.CryptoHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoHolder =
        CryptoHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_crypto,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: CryptoHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun setData(data: List<Crypto>) {
        submitList(data)
        notifyDataSetChanged()
    }

    class DiffCallback : DiffUtil.ItemCallback<Crypto>() {
        override fun areItemsTheSame(
            oldItem: Crypto,
            newItem: Crypto
        ) = oldItem.base == newItem.base && oldItem.counter == newItem.counter

        override fun areContentsTheSame(
            oldItem: Crypto,
            newItem: Crypto
        ) = oldItem.base == newItem.base && oldItem.counter == newItem.counter
    }

    inner class CryptoHolder(var viewDataBinding: ItemCryptoBinding) :
        BaseBindingViewHolder<Crypto>(viewDataBinding) {
        override fun bind(item: Crypto) {
            viewDataBinding.apply {
                setVariable(BR.crypto, item)
                lifecycleOwner = parentLifecycleOwner
                executePendingBindings()
            }
        }
    }
}
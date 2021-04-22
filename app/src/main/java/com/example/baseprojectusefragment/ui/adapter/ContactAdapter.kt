package com.example.baseprojectusefragment.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import com.example.baseprojectusefragment.BR
import com.example.baseprojectusefragment.R
import com.example.baseprojectusefragment.databinding.ItemContactBinding
import com.example.baseprojectusefragment.ui.base.BaseBindingAdapter
import com.example.baseprojectusefragment.ui.base.BaseBindingViewHolder
import com.example.baseprojectusefragment.ui.model.Contact
import java.util.*

class ContactAdapter(val parentLifecycleOwner: LifecycleOwner) :
    BaseBindingAdapter<Contact, ContactAdapter.ContactHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactHolder =
        ContactHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_contact,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ContactHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun setData(data: List<Contact>) {
        submitList(data)
        notifyDataSetChanged()
    }

    class DiffCallback : DiffUtil.ItemCallback<Contact>() {
        override fun areItemsTheSame(
            oldItem: Contact,
            newItem: Contact
        ): Boolean {
            return oldItem.number == newItem.number
        }

        override fun areContentsTheSame(
            oldItem: Contact,
            newItem: Contact
        ) = oldItem == newItem
    }

    inner class ContactHolder(private var viewDataBinding: ItemContactBinding) :
        BaseBindingViewHolder<Contact>(viewDataBinding) {
        override fun bind(item: Contact) {
            viewDataBinding.apply {
                setVariable(BR.contact, item)
                lifecycleOwner = parentLifecycleOwner
                executePendingBindings()
            }
        }
    }
}
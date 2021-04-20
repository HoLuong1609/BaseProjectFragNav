package com.example.baseprojectusefragment.ui.viewmodel

import android.app.Application
import com.example.baseprojectusefragment.common.DataHelper
import com.example.baseprojectusefragment.extensions.add
import com.example.baseprojectusefragment.ui.base.BaseViewModel
import com.example.baseprojectusefragment.ui.base.SingleLiveEvent
import com.example.baseprojectusefragment.ui.model.Contact
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ContactsViewModel(application: Application): BaseViewModel(application) {

    val contactList = SingleLiveEvent<List<Contact>>().apply { value = listOf() }
    val loading = SingleLiveEvent<Boolean>()

    fun fetchContacts() {
        DataHelper.getContactList(context)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { loading.value = true }
            .doFinally { loading.value = false }
            .subscribe({
                onResponse(it)
            }, {
                handleError(it)
            }).add(subscriptions)
    }

    private fun onResponse(list: List<Contact>) {
        contactList.value = list
    }
}
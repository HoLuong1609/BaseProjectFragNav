package com.example.baseprojectusefragment.ui.base

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.baseprojectusefragment.ui.viewmodel.ContactsViewModel
import com.example.baseprojectusefragment.ui.viewmodel.HomeViewModel
import com.example.baseprojectusefragment.ui.viewmodel.MainViewModel

class ViewModelFactory(context: Context) : ViewModelProvider.NewInstanceFactory() {
    private val application = when (context) {
        is Activity -> context.application
        else -> throw IllegalStateException("unknown apllication: $context")
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        with(modelClass) {
            when {
                isAssignableFrom(MainViewModel::class.java) -> {
                    MainViewModel(application)
                }
                isAssignableFrom(HomeViewModel::class.java) -> {
                    HomeViewModel(application)
                }
                isAssignableFrom(ContactsViewModel::class.java) -> {
                    ContactsViewModel(application)
                }
                else -> throw IllegalStateException("unknown view model: $modelClass")
            }
        } as T

    companion object {
        fun getInstance(activity: Context) = ViewModelFactory(activity)
    }
}
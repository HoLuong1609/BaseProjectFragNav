package com.example.baseprojectusefragment.ui.base

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.baseprojectusefragment.data.RetrofitClient
import com.example.baseprojectusefragment.data.api.CryptoService
import com.example.baseprojectusefragment.data.repository.CryptoRepos
import com.example.baseprojectusefragment.data.repository.CryptoReposImpl
import com.example.baseprojectusefragment.ui.viewmodel.CryptoViewModel
import com.example.baseprojectusefragment.ui.viewmodel.HomeViewModel
import com.example.baseprojectusefragment.ui.viewmodel.MainViewModel
import com.example.baseprojectusefragment.ui.viewmodel.MarketsViewModel

class ViewModelFactory(
    context: Context,
    private val cryptoRepos: CryptoRepos
) : ViewModelProvider.NewInstanceFactory() {
    private val application = when (context) {
        is Activity -> context.application
        is Fragment -> context.requireActivity().application
        else -> throw IllegalStateException("unknown apllication: $context")
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        with(modelClass) {
            when {
                isAssignableFrom(MainViewModel::class.java) -> {
                    MainViewModel(application)
                }
                isAssignableFrom(HomeViewModel::class.java) -> {
                    HomeViewModel(application)
                }
                isAssignableFrom(MarketsViewModel::class.java) -> {
                    MarketsViewModel(application)
                }
                isAssignableFrom(CryptoViewModel::class.java) -> {
                    CryptoViewModel(application, cryptoRepos)
                }
                else -> throw IllegalStateException("unknown view model: $modelClass")
            }
        } as T

    companion object {
        fun getInstance(activity: Context) = ViewModelFactory(
            activity,
            CryptoReposImpl.getInstance(RetrofitClient.getInstance()[CryptoService::class.java])
        )
    }
}
package com.example.baseprojectusefragment.ui.viewmodel

import android.app.Application
import com.example.baseprojectusefragment.data.repository.CryptoRepos
import com.example.baseprojectusefragment.data.request.CryptoRequest
import com.example.baseprojectusefragment.data.response.Crypto
import com.example.baseprojectusefragment.data.response.CryptoResponse
import com.example.baseprojectusefragment.extensions.add
import com.example.baseprojectusefragment.ui.base.BaseViewModel
import com.example.baseprojectusefragment.ui.base.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CryptoViewModel(application: Application, private val repos: CryptoRepos) :
    BaseViewModel(application) {

    var loading = SingleLiveEvent<Boolean>()
    val list = mutableListOf<Crypto>()
    val cryptos = SingleLiveEvent<List<Crypto>>().apply { value = list }

    fun getCryptos() {
        val request = CryptoRequest()
        repos.getCryptos(request)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { loading.value = true }
            .doFinally { loading.value = false }
            .subscribe({ data -> onResponse(data) }, {
                handleError(it)
            }).add(subscriptions)
    }

    private fun onResponse(response: CryptoResponse) {
        response.data?.let {
            list.clear()
            list.addAll(it)
            cryptos.value = list
        }
    }
}
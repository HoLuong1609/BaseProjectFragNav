package com.example.baseprojectusefragment.ui.viewmodel

import android.app.Application
import com.example.baseprojectusefragment.data.repository.CryptoRepos
import com.example.baseprojectusefragment.data.request.CryptoRequest
import com.example.baseprojectusefragment.data.response.CryptoResponse
import com.example.baseprojectusefragment.ui.base.BaseViewModel
import com.example.baseprojectusefragment.ui.base.SingleLiveEvent
import com.example.baseprojectusefragment.ui.model.Crypto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CryptoViewModel(application: Application, private val repos: CryptoRepos) :
    BaseViewModel(application) {

    var loading = SingleLiveEvent<Boolean>()
    val list = mutableListOf<Crypto>()
    val cryptos = SingleLiveEvent<List<Crypto>>().apply { value = list }
    private var mKeyword: String = ""

    fun getCryptos() {
        CoroutineScope(Dispatchers.Main).launch {
            loading.value = true
            val request = CryptoRequest()
            val data = withContext(Dispatchers.IO) {
                repos.getCryptos(request)
            }
            onResponse(data)
            loading.value = false
        }
    }

    private fun onResponse(response: CryptoResponse) {
        list.clear()
        list.addAll(response.data)
        filter(mKeyword)
    }

    fun filter(keyword: String) {
        mKeyword = keyword
        cryptos.value = list.filter { mKeyword.isEmpty() || it.name.contains(mKeyword) || it.base.contains(mKeyword) }
    }
}
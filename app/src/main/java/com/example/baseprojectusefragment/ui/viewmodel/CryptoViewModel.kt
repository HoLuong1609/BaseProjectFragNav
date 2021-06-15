package com.example.baseprojectusefragment.ui.viewmodel

import android.app.Application
import com.example.baseprojectusefragment.data.repository.CryptoRepos
import com.example.baseprojectusefragment.data.request.CryptoRequest
import com.example.baseprojectusefragment.data.response.CryptoResponse
import com.example.baseprojectusefragment.ui.base.BaseViewModel
import com.example.baseprojectusefragment.ui.base.SingleLiveEvent
import com.example.baseprojectusefragment.ui.model.Crypto
import kotlinx.coroutines.*

class CryptoViewModel(application: Application, private val repos: CryptoRepos) :
    BaseViewModel(application) {

    var loading = SingleLiveEvent<Boolean>()
    private val list = mutableListOf<Crypto>()
    val cryptoList = SingleLiveEvent<List<Crypto>>().apply { value = list }
    private var mKeyword: String = ""
    private var mJob: Job? = null

    fun filter(keyword: String) {
        mKeyword = keyword
        cryptoList.value = list.filter {
            mKeyword.isEmpty() || it.name.contains(mKeyword, true) || it.base.contains(mKeyword, true)
        }
    }

    fun startGetCryptoListJob() {
        mJob = repeatRequest(DELAY_TIME, ::getCryptoList)
    }

    fun cancelJob() {
        mJob?.cancel()
    }

    private fun getCryptoList() {
        CoroutineScope(Dispatchers.Main).launch {
            loading.value = true
            val request = CryptoRequest()
            val data = withTimeoutOrNull(TIMEOUT) {
                repos.getCryptoList(request)
            }
            data?.let { onResponse(it) }
            loading.value = false
        }
    }

    private fun onResponse(response: CryptoResponse) {
        list.clear()
        list.addAll(response.data)
        filter(mKeyword)
    }

    private fun repeatRequest(timeInterval: Long, job: () -> Unit): Job {
        return CoroutineScope(Dispatchers.IO).launch {
            while (isActive) {
                job.invoke()
                delay(timeInterval)
            }
        }
    }

    companion object {
        const val DELAY_TIME = 30000L
        const val TIMEOUT = 20000L
    }
}
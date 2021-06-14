package com.example.baseprojectusefragment.ui.model

import androidx.lifecycle.LiveData

class CryptoFilterLiveData : LiveData<CryptoFilter>() {
    private val filter: CryptoFilter by lazy { CryptoFilter() }

    fun search(keyword: String) {
        if (filter.searchKeyword != keyword) {
            filter.searchKeyword = keyword
        }
    }

    fun clear() {
        filter.searchKeyword = ""
    }

    fun call() {
        value = filter
    }
}
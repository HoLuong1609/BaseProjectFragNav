package com.example.baseprojectusefragment.data.repository

import com.example.baseprojectusefragment.data.api.CryptoService
import com.example.baseprojectusefragment.data.request.CryptoRequest

class CryptoReposImpl private constructor(private val service: CryptoService) :
    CryptoRepos {

    override suspend fun getCryptos(request: CryptoRequest) =
        service.getCryptos(request.counterCurrency)

    companion object {
        private var INSTANCE: CryptoReposImpl? = null

        fun getInstance(service: CryptoService): CryptoReposImpl = INSTANCE
            ?: CryptoReposImpl(service).also {
                INSTANCE = it
            }
    }
}
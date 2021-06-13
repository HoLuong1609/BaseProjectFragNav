package com.example.baseprojectusefragment.data.repository

import com.example.baseprojectusefragment.data.api.CryptoService
import com.example.baseprojectusefragment.data.request.CryptoRequest
import com.example.baseprojectusefragment.data.response.CryptoResponse
import io.reactivex.Observable

class CryptoReposImpl private constructor(private val service: CryptoService) :
    CryptoRepos {

    override fun getCryptos(request: CryptoRequest): Observable<CryptoResponse> =
        service.getCryptos(request.counterCurrency)

    companion object {
        private var INSTANCE: CryptoReposImpl? = null

        fun getInstance(service: CryptoService): CryptoReposImpl = INSTANCE
            ?: CryptoReposImpl(service).also {
                INSTANCE = it
            }
    }
}
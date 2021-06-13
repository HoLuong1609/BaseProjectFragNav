package com.example.baseprojectusefragment.data.repository

import com.example.baseprojectusefragment.data.request.CryptoRequest
import com.example.baseprojectusefragment.data.response.CryptoResponse
import io.reactivex.Observable
import retrofit2.http.Body

interface CryptoRepos {
    fun getCryptos(request: CryptoRequest): Observable<CryptoResponse>
}
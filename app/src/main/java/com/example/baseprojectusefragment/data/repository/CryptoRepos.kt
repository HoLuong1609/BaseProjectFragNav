package com.example.baseprojectusefragment.data.repository

import com.example.baseprojectusefragment.data.request.CryptoRequest
import com.example.baseprojectusefragment.data.response.CryptoResponse

interface CryptoRepos {
    suspend fun getCryptos(request: CryptoRequest): CryptoResponse
}
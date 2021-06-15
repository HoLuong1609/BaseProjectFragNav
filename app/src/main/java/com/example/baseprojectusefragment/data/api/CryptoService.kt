package com.example.baseprojectusefragment.data.api

import com.example.baseprojectusefragment.data.response.CryptoResponse
import retrofit2.http.*

interface CryptoService {

    @GET("price/all_prices_for_mobile")
    suspend fun getCryptoList(@Query("counter_currency") currency: String): CryptoResponse
}
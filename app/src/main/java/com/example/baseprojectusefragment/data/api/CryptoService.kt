package com.example.baseprojectusefragment.data.api

import com.example.baseprojectusefragment.data.response.CryptoResponse
import io.reactivex.Observable
import retrofit2.http.*

interface CryptoService {

    @GET("price/all_prices_for_mobile")
    fun getCryptos(@Query("counter_currency") currency: String): Observable<CryptoResponse>
}
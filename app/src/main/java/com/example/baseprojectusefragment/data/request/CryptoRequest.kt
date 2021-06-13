package com.example.baseprojectusefragment.data.request

import com.google.gson.annotations.SerializedName

class CryptoRequest(
    @SerializedName("counter_currency") var counterCurrency: String = "USD"
)
package com.example.baseprojectusefragment.data.response

import com.example.baseprojectusefragment.ui.model.Crypto
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CryptoResponse {
    @SerializedName("data")
    @Expose
    var data: List<Crypto> = listOf()
}
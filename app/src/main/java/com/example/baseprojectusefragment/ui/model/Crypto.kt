package com.example.baseprojectusefragment.ui.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Crypto {
    @SerializedName("base")
    @Expose
    var base: String = ""

    @SerializedName("counter")
    @Expose
    var counter: String = ""

    @SerializedName("buy_price")
    @Expose
    var buyPrice: Double = 0.0

    @SerializedName("sell_price")
    @Expose
    var sellPrice: Double = 0.0

    @SerializedName("icon")
    @Expose
    var icon: String = ""

    @SerializedName("name")
    @Expose
    var name: String = ""
}
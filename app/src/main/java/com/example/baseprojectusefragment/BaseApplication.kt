package com.example.baseprojectusefragment

import androidx.multidex.MultiDexApplication

class BaseApplication: MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        HOLDER.INSTANCE = this
    }

    private object HOLDER {
        lateinit var INSTANCE: BaseApplication
    }

    companion object {
        val instance: BaseApplication by lazy { HOLDER.INSTANCE }
    }
}
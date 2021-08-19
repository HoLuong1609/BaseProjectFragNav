package com.example.baseprojectusefragment.ui.viewmodel

import android.app.Application
import com.example.baseprojectusefragment.ui.base.BaseViewModel
import com.example.baseprojectusefragment.ui.base.SingleLiveEvent

class HomeViewModel(application: Application): BaseViewModel(application) {
    val isEnabled: SingleLiveEvent<Boolean> = SingleLiveEvent<Boolean>().apply { value = true}

}
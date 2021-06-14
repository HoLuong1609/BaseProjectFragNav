package com.example.baseprojectusefragment.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<VM : BaseViewModel, VB : ViewDataBinding> : AppCompatActivity() {
    private lateinit var internalViewModel: VM
    private lateinit var viewBinding: VB
    private val viewModel: VM
        get() = internalViewModel

    val bindingView: VB
        get() = viewBinding

    abstract fun onCreateViewModel(): VM

    abstract fun layoutId(): Int
    abstract fun initView(savedInstanceState: Bundle?)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = DataBindingUtil.setContentView(this, layoutId())
        internalViewModel = onCreateViewModel()
        initView(savedInstanceState)
    }

    private fun showErrorDialog(message: String) {
        this.showErrorDialog(message)
    }
}
package com.example.baseprojectusefragment.ui.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.databinding.ViewDataBinding
import com.example.baseprojectusefragment.R
import com.example.baseprojectusefragment.ui.fragments.*
import com.example.baseprojectusefragment.common.*
import com.example.baseprojectusefragment.extensions.hideSoftKeyboard
import com.example.baseprojectusefragment.ui.base.BaseActivity
import com.example.baseprojectusefragment.ui.base.BaseViewModel
import com.example.baseprojectusefragment.ui.base.FragmentNavigation
import com.ncapdevi.fragnav.FragNavController
import com.ncapdevi.fragnav.FragNavLogger
import com.ncapdevi.fragnav.FragNavSwitchController
import com.ncapdevi.fragnav.FragNavTransactionOptions
import com.ncapdevi.fragnav.tabhistory.UniqueTabHistoryStrategy
import kotlinx.android.synthetic.main.activity_main.*

abstract class FragNavActivity<VM : BaseViewModel, VB : ViewDataBinding> : BaseActivity<VM, VB>(), FragNavController.TransactionListener,
    FragNavController.RootFragmentListener, FragmentNavigation, FragNavSwitchController {

    private val fragNavController: FragNavController = FragNavController(supportFragmentManager,
        R.id.container
    )

    override fun initView(savedInstanceState: Bundle?) {
        fragNavController.apply {
            transactionListener = this@FragNavActivity
            rootFragmentListener = this@FragNavActivity
            createEager = true
            fragNavLogger = object : FragNavLogger {
                override fun error(message: String, throwable: Throwable) {
                    Log.e(TAG, message, throwable)
                }
            }

            defaultTransactionOptions = FragNavTransactionOptions.newBuilder()
                .customAnimations(
                    enterAnimation = R.anim.fragment_enter,
                    exitAnimation = R.anim.fragment_exit,
                    popEnterAnimation = R.anim.fragment_pop_enter,
                    popExitAnimation = R.anim.fragment_pop_exit
                )
                .build()
            fragmentHideStrategy = FragNavController.DETACH_ON_NAVIGATE_HIDE_ON_SWITCH

            navigationStrategy = UniqueTabHistoryStrategy(this@FragNavActivity)
        }

        fragNavController.initialize(defaultTabIndex, savedInstanceState)
    }

    abstract val defaultTabIndex: Int

    override fun onBackPressed() {
        if (fragNavController.isRootFragment.not()) {
            if (fragNavController.popFragment().not()) {
                super.onBackPressed()
            }
        } else {
            super.onBackPressed()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        fragNavController.onSaveInstanceState(outState)

    }

    override fun onTabTransaction(fragment: Fragment?, index: Int) {
        // If we have a backstack, show the back button
        supportActionBar?.setDisplayHomeAsUpEnabled(fragNavController.isRootFragment.not())

    }

    override fun onFragmentTransaction(fragment: Fragment?, transactionType: FragNavController.TransactionType) {
        //do fragmentty stuff. Maybe change title, I'm not going to tell you how to live your life
        // If we have a backstack, show the back button
        supportActionBar?.setDisplayHomeAsUpEnabled(fragNavController.isRootFragment.not())

    }

    override fun pushFragment(fragment: Fragment, sharedElementList: List<Pair<View, String>>?) {
        val options = FragNavTransactionOptions.newBuilder()
        sharedElementList?.let {
            it.forEach { pair ->
                options.addSharedElement(pair)
            }
        }
        fragNavController.pushFragment(fragment, options.build())
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> fragNavController.popFragment()
        }
        return true
    }

    fun switchTab(tabIndex: Int) {
        hideSoftKeyboard()
        fragNavController.switchTab(tabIndex, null)
    }

    fun getCurrentFragment() = fragNavController.currentFrag

    companion object {
        private val TAG = this::class.java.simpleName
    }
}




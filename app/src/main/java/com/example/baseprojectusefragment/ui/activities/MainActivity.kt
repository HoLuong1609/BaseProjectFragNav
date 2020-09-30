package com.example.baseprojectusefragment.ui.activities

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.util.Log
import android.view.MenuItem
import android.view.View
import com.example.baseprojectusefragment.R
import com.example.baseprojectusefragment.ui.fragments.*
import com.example.baseprojectusefragment.common.*
import com.example.baseprojectusefragment.databinding.ActivityMainBinding
import com.example.baseprojectusefragment.extensions.hideSoftKeyboard
import com.example.baseprojectusefragment.extensions.initViewModel
import com.example.baseprojectusefragment.ui.base.BaseActivity
import com.example.baseprojectusefragment.ui.base.FragmentNavigation
import com.example.baseprojectusefragment.ui.viewmodel.MainViewModel
import com.ncapdevi.fragnav.FragNavController
import com.ncapdevi.fragnav.FragNavLogger
import com.ncapdevi.fragnav.FragNavSwitchController
import com.ncapdevi.fragnav.FragNavTransactionOptions
import com.ncapdevi.fragnav.tabhistory.UniqueTabHistoryStrategy
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(), FragNavController.TransactionListener,
    FragNavController.RootFragmentListener, FragmentNavigation {

    override val numberOfRootFragments: Int = 5

    private val fragNavController: FragNavController = FragNavController(supportFragmentManager,
        R.id.container
    )

    override fun onCreateViewModel() = initViewModel<MainViewModel>()

    override fun layoutId() = R.layout.activity_main

    override fun initView(savedInstanceState: Bundle?) {
        fragNavController.apply {
            transactionListener = this@MainActivity
            rootFragmentListener = this@MainActivity
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

            navigationStrategy = UniqueTabHistoryStrategy(object : FragNavSwitchController {
                override fun switchTab(index: Int, transactionOptions: FragNavTransactionOptions?) {
                    navBottom.selectedItemId = when(index) {
                        0 -> R.id.navigation_home
                        1 -> R.id.navigation_favorites
                        2 -> R.id.navigation_nearby
                        3 -> R.id.navigation_friends
                        4 -> R.id.navigation_food
                        else -> R.id.navigation_home
                    }
                }
            })
        }

        fragNavController.initialize(INDEX_NEARBY, savedInstanceState)

        val initial = savedInstanceState == null
        if (initial) {
            navBottom.selectedItemId = R.id.navigation_home
        }

        navBottom.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> {
                    switchTab(INDEX_RECENTS)
                    setTitle(R.string.recents)
                }
                R.id.navigation_favorites -> {
                    switchTab(INDEX_FAVORITES)
                    setTitle(R.string.favorites)
                }
                R.id.navigation_nearby -> {
                    switchTab(INDEX_NEARBY)
                    setTitle(R.string.nearby)
                }
                R.id.navigation_friends -> {
                    switchTab(INDEX_FRIENDS)
                    setTitle(R.string.friends)
                }
                R.id.navigation_food -> {
                    switchTab(INDEX_FOOD)
                    setTitle(R.string.food)
                }
            }
            true
        }
    }

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

    override fun getRootFragment(index: Int): Fragment {
        when (index) {
            INDEX_RECENTS -> return HomeFragment.newInstance()
            INDEX_FAVORITES -> return HomeFragment.newInstance()
            INDEX_NEARBY -> return HomeFragment.newInstance()
            INDEX_FRIENDS -> return HomeFragment.newInstance()
            INDEX_FOOD -> return HomeFragment.newInstance()
        }
        throw IllegalStateException("Need to send an index that we know")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> fragNavController.popFragment()
        }
        return true
    }

    private fun switchTab(tabIndex: Int) {
        hideSoftKeyboard()
        fragNavController.switchTab(tabIndex, null)
    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }
}




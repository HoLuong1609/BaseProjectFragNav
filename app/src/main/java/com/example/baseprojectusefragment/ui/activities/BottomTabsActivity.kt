package com.example.baseprojectusefragment.ui.activities

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import android.view.View
import com.example.baseprojectusefragment.R
import com.example.baseprojectusefragment.ui.fragments.*
import com.example.baseprojectusefragment.common.*
import com.example.baseprojectusefragment.extensions.hideSoftKeyboard
import com.ncapdevi.fragnav.FragNavController
import com.ncapdevi.fragnav.FragNavLogger
import com.ncapdevi.fragnav.FragNavSwitchController
import com.ncapdevi.fragnav.FragNavTransactionOptions
import com.ncapdevi.fragnav.tabhistory.UniqueTabHistoryStrategy
import com.roughike.bottombar.BottomBar

class BottomTabsActivity : AppCompatActivity(), BaseFragment.FragmentNavigation, FragNavController.TransactionListener, FragNavController.RootFragmentListener {
    override val numberOfRootFragments: Int = 5

    private val fragNavController: FragNavController = FragNavController(supportFragmentManager,
        R.id.container
    )

    private lateinit var bottomBar: BottomBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_tabs)

         bottomBar = findViewById(R.id.bottomBar)

        fragNavController.apply {
            transactionListener = this@BottomTabsActivity
            rootFragmentListener = this@BottomTabsActivity
            createEager = true
            fragNavLogger = object : FragNavLogger {
                override fun error(message: String, throwable: Throwable) {
                    Log.e(TAG, message, throwable)
                }
            }

            defaultTransactionOptions = FragNavTransactionOptions.newBuilder()
//                .customAnimations(R.anim.slide_in_from_right, R.anim.slide_out_to_left, R.anim.slide_in_from_left, R.anim.slide_out_to_right)
                .build()
            fragmentHideStrategy = FragNavController.DETACH_ON_NAVIGATE_HIDE_ON_SWITCH

            navigationStrategy = UniqueTabHistoryStrategy(object : FragNavSwitchController {
                override fun switchTab(index: Int, transactionOptions: FragNavTransactionOptions?) {
                    bottomBar.selectTabAtPosition(index)
                }
            })
        }

        fragNavController.initialize(INDEX_NEARBY, savedInstanceState)

        val initial = savedInstanceState == null
        if (initial) {
            bottomBar.selectTabAtPosition(INDEX_NEARBY)
        }

        bottomBar.setOnTabSelectListener({ tabId ->
            when (tabId) {
                R.id.bb_menu_recents -> switchTab(bottomBar.getTabWithId(tabId), INDEX_RECENTS)
                R.id.bb_menu_favorites -> switchTab(bottomBar.getTabWithId(tabId), INDEX_FAVORITES)
                R.id.bb_menu_nearby -> switchTab(bottomBar.getTabWithId(tabId), INDEX_NEARBY)
                R.id.bb_menu_friends -> switchTab(bottomBar.getTabWithId(tabId), INDEX_FRIENDS)
                R.id.bb_menu_food -> switchTab(bottomBar.getTabWithId(tabId), INDEX_FOOD)
            }
        }, initial)

        bottomBar.setOnTabReselectListener { fragNavController.clearStack() }
    }

    override fun onBackPressed() {
        if (fragNavController.popFragment().not()) {
            super.onBackPressed()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        fragNavController.onSaveInstanceState(outState)

    }

    override fun pushFragment(fragment: Fragment, sharedElementList: List<Pair<View, String>>?) {
        val options = FragNavTransactionOptions.newBuilder()
        options.reordering = true
        sharedElementList?.let {
            it.forEach { pair ->
                options.addSharedElement(pair)
            }
        }
        fragNavController.pushFragment(fragment, options.build())

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

    override fun getRootFragment(index: Int): Fragment {
        when (index) {
            INDEX_RECENTS -> return RecentsFragment.newInstance(0)
            INDEX_FAVORITES -> return FavoritesFragment.newInstance(0)
            INDEX_NEARBY -> return NearbyFragment.newInstance(0)
            INDEX_FRIENDS -> return FriendsFragment.newInstance(0)
            INDEX_FOOD -> return FoodFragment.newInstance(0)
        }
        throw IllegalStateException("Need to send an index that we know")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> fragNavController.popFragment()
        }
        return true
    }

    private fun switchTab(view: View, tabIndex: Int) {
        hideSoftKeyboard()
        if (view.isSelected)
            fragNavController.clearStack(null)
        else
            fragNavController.switchTab(tabIndex, null)
    }

    companion object {
        private val TAG = BottomTabsActivity::class.java.simpleName
    }
}




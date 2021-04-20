package com.example.baseprojectusefragment.ui.activities

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.baseprojectusefragment.R
import com.example.baseprojectusefragment.common.*
import com.example.baseprojectusefragment.databinding.ActivityMainBinding
import com.example.baseprojectusefragment.extensions.initViewModel
import com.example.baseprojectusefragment.ui.base.FragNavActivity
import com.example.baseprojectusefragment.ui.base.FragmentNavigation
import com.example.baseprojectusefragment.ui.fragments.ContactsFragment
import com.example.baseprojectusefragment.ui.fragments.HomeFragment
import com.example.baseprojectusefragment.ui.viewmodel.MainViewModel
import com.ncapdevi.fragnav.FragNavController
import com.ncapdevi.fragnav.FragNavTransactionOptions
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : FragNavActivity<MainViewModel, ActivityMainBinding>(), FragNavController.TransactionListener,
    FragNavController.RootFragmentListener, FragmentNavigation {

    override val numberOfRootFragments: Int = 5

    override fun onCreateViewModel() = initViewModel<MainViewModel>()

    override fun layoutId() = R.layout.activity_main

    override val defaultTabIndex: Int
        get() = INDEX_FRIENDS

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
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

    override fun getRootFragment(index: Int): Fragment {
        when (index) {
            INDEX_RECENTS -> return HomeFragment.newInstance()
            INDEX_FAVORITES -> return HomeFragment.newInstance()
            INDEX_NEARBY -> return HomeFragment.newInstance()
            INDEX_FRIENDS -> return ContactsFragment.newInstance()
            INDEX_FOOD -> return HomeFragment.newInstance()
        }
        throw IllegalStateException("Need to send an index that we know")
    }
}




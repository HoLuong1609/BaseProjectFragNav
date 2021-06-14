package com.example.baseprojectusefragment.ui.activities

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import com.example.baseprojectusefragment.R
import com.example.baseprojectusefragment.common.*
import com.example.baseprojectusefragment.databinding.ActivityMainBinding
import com.example.baseprojectusefragment.extensions.initViewModel
import com.example.baseprojectusefragment.ui.base.FragNavActivity
import com.example.baseprojectusefragment.ui.base.FragmentNavigation
import com.example.baseprojectusefragment.ui.fragments.HomeFragment
import com.example.baseprojectusefragment.ui.fragments.MarketsFragment
import com.example.baseprojectusefragment.ui.viewmodel.MainViewModel
import com.miguelcatalan.materialsearchview.MaterialSearchView
import com.ncapdevi.fragnav.FragNavController
import com.ncapdevi.fragnav.FragNavController.Companion.NO_TAB
import com.ncapdevi.fragnav.FragNavTransactionOptions
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : FragNavActivity<MainViewModel, ActivityMainBinding>(), FragNavController.TransactionListener,
    FragNavController.RootFragmentListener, FragmentNavigation,
    MaterialSearchView.OnQueryTextListener {

    override val numberOfRootFragments: Int = 4

    override fun onCreateViewModel() = initViewModel<MainViewModel>()

    override fun layoutId() = R.layout.activity_main

    override val defaultTabIndex: Int
        get() = INDEX_MARKETS

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        val initial = savedInstanceState == null
        if (initial) {
            navBottom.selectedItemId = R.id.navigation_markets
        }

        navBottom.setOnNavigationItemSelectedListener {
            when (it.itemId) {
               R.id.navigation_markets -> {
                    switchTab(INDEX_MARKETS)
                    setTitle(R.string.markets)
                }
                R.id.navigation_trades -> {
                    switchTab(INDEX_TRADES)
                    setTitle(R.string.trades)
                }
                R.id.navigation_futures -> {
                    switchTab(INDEX_FUTURES)
                    setTitle(R.string.futures)
                }
                R.id.navigation_wallets -> {
                    switchTab(INDEX_WALLETS)
                    setTitle(R.string.wallets)
                }
            }
            true
        }
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        searchView.setBackIcon(AppCompatResources.getDrawable(this, R.drawable.ic_action_navigation_arrow_back))
        searchView.setOnQueryTextListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val itemSearch = menu.findItem(R.id.action_search)
        searchView.setMenuItem(itemSearch)
        return true
    }

    override fun switchTab(index: Int, transactionOptions: FragNavTransactionOptions?) {
        navBottom.selectedItemId = when(index) {
            0 -> R.id.navigation_markets
            1 -> R.id.navigation_trades
            2 -> R.id.navigation_futures
            3 -> R.id.navigation_wallets
            else -> R.id.navigation_markets
        }
    }

    override fun getRootFragment(index: Int): Fragment {
        when (index) {
            INDEX_MARKETS -> return MarketsFragment.newInstance()
            INDEX_TRADES -> return HomeFragment.newInstance()
            INDEX_FUTURES -> return HomeFragment.newInstance()
            INDEX_WALLETS -> return HomeFragment.newInstance()
        }
        throw IllegalStateException("Need to send an index that we know")
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        newText?.let {
            val fragment = getCurrentFragment()
            if (fragment is MarketsFragment && fragment.isAdded) {
                fragment.search(it)
            }
        }
        return true
    }

    private fun getTabIndex(itemId: Int) = when(itemId) {
        R.id.navigation_markets -> INDEX_MARKETS
        R.id.navigation_trades -> INDEX_TRADES
        R.id.navigation_futures -> INDEX_FUTURES
        R.id.navigation_wallets -> INDEX_WALLETS
        else -> NO_TAB
    }
}




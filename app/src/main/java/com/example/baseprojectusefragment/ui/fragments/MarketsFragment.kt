package com.example.baseprojectusefragment.ui.fragments

import android.os.Bundle
import android.view.View
import com.example.baseprojectusefragment.R
import com.example.baseprojectusefragment.databinding.FragmentMarketsBinding
import com.example.baseprojectusefragment.extensions.initViewModel
import com.example.baseprojectusefragment.ui.adapter.TabPagerAdapter
import com.example.baseprojectusefragment.ui.base.BaseFragment
import com.example.baseprojectusefragment.ui.viewmodel.MarketsViewModel
import kotlinx.android.synthetic.main.fragment_markets.*

class MarketsFragment : BaseFragment<MarketsViewModel, FragmentMarketsBinding>() {

    private var mPagerAdapter: TabPagerAdapter? = null

    override fun layoutId() = R.layout.fragment_markets

    override fun onCreateViewModel() = initViewModel<MarketsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mPagerAdapter = TabPagerAdapter(requireActivity().supportFragmentManager, resources.getStringArray(R.array.markets_tab_titles).toList())
        viewPager.adapter = mPagerAdapter
        tabLayout.setupWithViewPager(viewPager)
    }

    fun search(keyword: String) {
        val fragment = mPagerAdapter?.getFragment(viewPager.currentItem)
        if (fragment is CrytosFragment && fragment.isAdded) {
            fragment.search(keyword)
        }
    }

    companion object {

        fun newInstance() = MarketsFragment().apply {

        }
    }
}

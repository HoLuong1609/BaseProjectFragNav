package com.example.baseprojectusefragment.ui.adapter

import android.view.ViewGroup
import androidx.collection.SparseArrayCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.baseprojectusefragment.ui.fragments.CrytosFragment

class TabPagerAdapter(fm: FragmentManager, private val mTitles: List<String>) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val mSparseArray = SparseArrayCompat<Fragment>()

    override fun getItem(position: Int): Fragment = when (position) {
        0 -> CrytosFragment.newInstance()
        else -> CrytosFragment.newInstance(true)
    }

    override fun getCount(): Int {
        return mTitles.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val fragment = super.instantiateItem(container, position) as Fragment
        mSparseArray.put(position, fragment)
        return fragment
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        super.destroyItem(container, position, `object`)
        mSparseArray.remove(position)
    }

    fun getFragment(position: Int): Fragment? {
        return mSparseArray.get(position)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mTitles[position]
    }
}

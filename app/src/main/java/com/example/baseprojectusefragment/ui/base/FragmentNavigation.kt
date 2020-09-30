package com.example.baseprojectusefragment.ui.base

import android.view.View
import androidx.fragment.app.Fragment

interface FragmentNavigation {
    fun pushFragment(fragment: Fragment, sharedElementList: List<Pair<View, String>>?= null)
}
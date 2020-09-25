package com.example.baseprojectusefragment.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View

/**
 * Created by niccapdevila on 3/26/16.
 */
class FriendsFragment : BaseFragment() {


    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn.setOnClickListener {
            mFragmentNavigation.pushFragment(newInstance(mInt + 1))
        }
        btn.text = """${javaClass.simpleName} $mInt"""
    }

    companion object {

        fun newInstance(instance: Int): FriendsFragment {
            val args = Bundle()
            args.putInt(ARGS_INSTANCE, instance)
            val fragment = FriendsFragment()
            fragment.arguments = args
            return fragment
        }
    }
}

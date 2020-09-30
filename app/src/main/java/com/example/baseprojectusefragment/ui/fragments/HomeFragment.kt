package com.example.baseprojectusefragment.ui.fragments

import android.os.Bundle
import android.view.View
import com.example.baseprojectusefragment.R
import com.example.baseprojectusefragment.databinding.FragmentHomeBinding
import com.example.baseprojectusefragment.extensions.initViewModel
import com.example.baseprojectusefragment.ui.base.BaseFragment
import com.example.baseprojectusefragment.ui.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>() {

    override fun layoutId() = R.layout.fragment_home

    override fun onCreateViewModel() = initViewModel<HomeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val instance = arguments?.getInt(ARGS_INSTANCE) ?: 0 + 1
        button.setOnClickListener {
            fragmentNavigation.pushFragment(
                newInstance(instance + 1)
            )
        }
        editText.setText(instance.toString())
    }

    companion object {

        private const val ARGS_INSTANCE = "args_instance"
        fun newInstance(instance: Int = 0) = HomeFragment().apply {
            arguments = Bundle().apply {
                putInt(ARGS_INSTANCE, instance)
            }
        }
    }
}
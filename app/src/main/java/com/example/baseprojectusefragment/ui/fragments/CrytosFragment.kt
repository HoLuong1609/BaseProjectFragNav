package com.example.baseprojectusefragment.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.baseprojectusefragment.R
import com.example.baseprojectusefragment.databinding.FragmentCryptosBinding
import com.example.baseprojectusefragment.extensions.initViewModel
import com.example.baseprojectusefragment.ui.adapter.CryptoAdapter
import com.example.baseprojectusefragment.ui.base.BaseFragment
import com.example.baseprojectusefragment.ui.viewmodel.CryptoViewModel
import kotlinx.android.synthetic.main.fragment_cryptos.*

class CrytosFragment : BaseFragment<CryptoViewModel, FragmentCryptosBinding>() {

    override fun layoutId() = R.layout.fragment_cryptos

    override fun onCreateViewModel() = initViewModel<CryptoViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val isFavorite = arguments?.getBoolean(ARGS_IS_FAVORITE)
        bindingView.viewmodel = viewModel

        rvCryptos.run {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = CryptoAdapter(this@CrytosFragment)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.run {
            startGetCryptoListJob()
            loading.observe(this@CrytosFragment, {
                swipeRefreshLayout.isRefreshing = it
            })
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.cancelJob()
    }

    fun search(keyword: String) {
        viewModel.filter(keyword)
    }

    companion object {

        private const val ARGS_IS_FAVORITE = "args_is_favorite"
        fun newInstance(isFavorite: Boolean = false) = CrytosFragment().apply {
            arguments = Bundle().apply {
                putBoolean(ARGS_IS_FAVORITE, isFavorite)
            }
        }
    }
}

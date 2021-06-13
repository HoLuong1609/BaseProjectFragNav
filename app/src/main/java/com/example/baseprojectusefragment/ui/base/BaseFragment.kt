package com.example.baseprojectusefragment.ui.base

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

abstract class BaseFragment<VM : BaseViewModel, VB : ViewDataBinding> : Fragment() {
    private val subscriptions = CompositeDisposable()

    private lateinit var internalViewModel: VM
    private lateinit var viewBinding: VB

    protected val viewModel
        get() = internalViewModel

    val bindingView: VB
        get() = viewBinding

    lateinit var fragmentNavigation: FragmentNavigation

    abstract fun layoutId(): Int
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        internalViewModel = onCreateViewModel()
        subscriptions.add(
            viewModel.getErrorMessage()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::showErrorDialog)
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentNavigation) {
            fragmentNavigation = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = DataBindingUtil.inflate(inflater, layoutId(), container, false)
        viewBinding.lifecycleOwner = this
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(
                android.R.transition.fade
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        subscriptions.clear()
    }

    abstract fun onCreateViewModel(): VM

    private fun showErrorDialog(message: String) {

    }
}
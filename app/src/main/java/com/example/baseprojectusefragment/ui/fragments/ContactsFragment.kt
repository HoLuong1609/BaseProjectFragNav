package com.example.baseprojectusefragment.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.baseprojectusefragment.R
import com.example.baseprojectusefragment.databinding.FragmentContactsBinding
import com.example.baseprojectusefragment.extensions.initViewModel
import com.example.baseprojectusefragment.ui.adapter.ContactAdapter
import com.example.baseprojectusefragment.ui.base.BaseFragment
import com.example.baseprojectusefragment.ui.viewmodel.ContactsViewModel
import kotlinx.android.synthetic.main.fragment_contacts.*

class ContactsFragment : BaseFragment<ContactsViewModel, FragmentContactsBinding>(),
    SwipeRefreshLayout.OnRefreshListener {

    private var adapter: ContactAdapter? = null

    override fun layoutId() = R.layout.fragment_contacts

    override fun onCreateViewModel() = initViewModel<ContactsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindingView.viewModel = viewModel
        setupRecyclerView()
        swipeRefreshLayout.setOnRefreshListener(this)
        viewModel.apply {
            loading.observe(viewLifecycleOwner) {
                swipeRefreshLayout.isRefreshing = it
            }
            fetchContacts()
        }
    }

    override fun onRefresh() {
        viewModel.fetchContacts()
    }

    private fun setupRecyclerView() {
        rvContacts.layoutManager = LinearLayoutManager(requireContext())
        rvContacts.setHasFixedSize(true)
        adapter = ContactAdapter(this)
        rvContacts.adapter = adapter
    }

    companion object {

        fun newInstance() = ContactsFragment()
    }
}

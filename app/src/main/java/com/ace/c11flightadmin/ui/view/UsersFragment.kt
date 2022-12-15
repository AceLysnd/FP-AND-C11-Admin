package com.ace.c11flightadmin.ui.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ace.c11flightadmin.R
import com.ace.c11flightadmin.data.model.Account
import com.ace.c11flightadmin.databinding.FragmentUsersBinding
import com.ace.c11flightadmin.ui.adapter.UserAdapter
import com.ace.c11flightadmin.ui.viewmodel.UsersFragmentViewModel

class UsersFragment : Fragment() {

    private var _binding: FragmentUsersBinding? = null
    private val binding get() = _binding!!

    private lateinit var userAdapter: UserAdapter
    private lateinit var userList: RecyclerView

    private val viewModel: UsersFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUsersBinding.inflate(inflater, container, false)
        return binding.root
//        return inflater.inflate(R.layout.fragment_users, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userList = requireActivity().findViewById(R.id.rv_users)
        userList.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.VERTICAL, false
        )

        setAdapter()
        observeData()

    }

    private fun setAdapter() {
        userAdapter = UserAdapter(mutableListOf()) { user -> showUserDetails(user)}
        userList.adapter = userAdapter
    }

    private fun showUserDetails(user: Account) {
        val intent = Intent(requireActivity(), UserDetailActivity::class.java)
        USER_ID = user.id!!

        startActivity(intent)
    }

    private fun observeData() {

        viewModel.getUsers()

        viewModel.loadingState.observe(viewLifecycleOwner) { isLoading ->
            binding.pbPost.isVisible = isLoading
            binding.rvUsers.isVisible = !isLoading
        }

        viewModel.errorState.observe(viewLifecycleOwner) { errorData ->
            binding.tvError.isVisible = errorData.first
            errorData.second?.message?.let {
                binding.tvError.text = it
            }
        }

        viewModel.userResult.observe(viewLifecycleOwner) {
            userAdapter.setItems(it.data.users)
        }
    }

    companion object {
        var USER_ID: Int = 0
    }
}
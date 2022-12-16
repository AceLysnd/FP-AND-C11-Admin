package com.ace.c11flightadmin.ui.view

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
import com.ace.c11flightadmin.data.model.Ticket
import com.ace.c11flightadmin.databinding.FragmentTicketBinding
import com.ace.c11flightadmin.databinding.FragmentUsersBinding
import com.ace.c11flightadmin.ui.adapter.TicketAdapter
import com.ace.c11flightadmin.ui.adapter.UserAdapter
import com.ace.c11flightadmin.ui.viewmodel.TicketFragmentViewModel
import com.ace.c11flightadmin.ui.viewmodel.UsersFragmentViewModel

class TicketFragment : Fragment() {

    private var _binding: FragmentTicketBinding? = null
    private val binding get() = _binding!!

    private lateinit var ticketAdapter: TicketAdapter
    private lateinit var ticketList: RecyclerView

    private val viewModel: TicketFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTicketBinding.inflate(inflater, container, false)
        return binding.root
//        return inflater.inflate(R.layout.fragment_users, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ticketList = requireActivity().findViewById(R.id.rv_tickets)
        ticketList.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.VERTICAL, false
        )

        setAdapter()
        observeData()

    }

    private fun setAdapter() {
        ticketAdapter = TicketAdapter(mutableListOf()) { ticket -> showTicketDetails(ticket)}
        ticketList.adapter = ticketAdapter
    }

    private fun showTicketDetails(ticket: Ticket) {
        val intent = Intent(requireActivity(), TicketDetailActivity::class.java)
        TICKET_ID = ticket.id!!

        startActivity(intent)
    }

    private fun observeData() {

        viewModel.getTickets()

        viewModel.loadingState.observe(viewLifecycleOwner) { isLoading ->
            binding.pbPost.isVisible = isLoading
            binding.rvTickets.isVisible = !isLoading
        }

        viewModel.errorState.observe(viewLifecycleOwner) { errorData ->
            binding.tvError.isVisible = errorData.first
            errorData.second?.message?.let {
                binding.tvError.text = it
            }
        }

        viewModel.ticketResult.observe(viewLifecycleOwner) {
            ticketAdapter.setItems(it.data)
        }
    }

    companion object {
        var TICKET_ID: Int = 0
    }
}
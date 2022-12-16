package com.ace.c11flightadmin.ui.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.ace.c11flightadmin.R
import com.ace.c11flightadmin.databinding.FragmentCreateBinding
import com.ace.c11flightadmin.databinding.FragmentTicketBinding
import com.ace.c11flightadmin.ui.adapter.TicketAdapter
import com.ace.c11flightadmin.ui.viewmodel.TicketFragmentViewModel

class CreateFragment : Fragment() {
    private var _binding: FragmentCreateBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCreateBinding.inflate(inflater, container, false)
        return binding.root
//        return inflater.inflate(R.layout.fragment_users, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnclickListeners()
    }

    private fun setOnclickListeners() {
        binding.btnTicket.setOnClickListener{
            val intent = Intent(requireActivity(), CreateTicketActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }

}
package com.ace.c11flightadmin.ui.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.ace.c11flightadmin.R
import com.ace.c11flightadmin.databinding.ActivityTicketDetailBinding
import com.ace.c11flightadmin.databinding.ActivityUserDetailBinding
import com.ace.c11flightadmin.ui.viewmodel.TicketDetailViewModel
import com.ace.c11flightadmin.ui.viewmodel.UserDetailViewModel
import com.bumptech.glide.Glide

class TicketDetailActivity : AppCompatActivity() {
    private var _binding: ActivityTicketDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TicketDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityTicketDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUserId()
        setUserInfo()
        setOnclickListeners()
    }

    private fun setUserId() {

    }

    private fun setUserInfo() {
        viewModel.getTicketData()
        viewModel.loadingState.observe(this) { isLoading ->
            binding.pbPost.isVisible = isLoading
        }

        viewModel.errorState.observe(this) { errorData ->
            binding.tvError.isVisible = errorData.first
            errorData.second?.message?.let {
                binding.tvError.text = it
            }
        }
        viewModel._ticketResult.observe(this) {
            binding.tvFlightId.text = it.data?.flightId.toString()
            binding.tvType.text = it.data?.type
            binding.tvPrice.text = it.data?.price.toString()
            binding.tvDesc.text = it.data?.desc.toString()
            binding.tvDateCreated.text = it.data?.createdAt.toString()
            binding.tvDateUpdated.text = it.data?.updatedAt.toString()
        }
    }

    private fun setOnclickListeners() {
        binding.btnDelete.setOnClickListener {
            createDialog()
        }

        binding.btnEditTicket.setOnClickListener {
            intent = Intent(this, EditUserActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun createDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete User")
        builder.setMessage("Delete this user?")
//builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))

        builder.setPositiveButton(android.R.string.yes) { dialog, which ->

//            viewModel.deleteUser()
            viewModel.loadingState.observe(this) { isLoading ->
                binding.pbPost.isVisible = isLoading
            }

            viewModel.errorState.observe(this) { errorData ->
                binding.tvError.isVisible = errorData.first
                errorData.second?.message?.let {
                    binding.tvError.text = it
                }
            }
            Toast.makeText(
                applicationContext,
                "User deleted", Toast.LENGTH_SHORT
            ).show()
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        builder.setNegativeButton(android.R.string.no) { dialog, which ->

        }
        builder.show()
    }
}
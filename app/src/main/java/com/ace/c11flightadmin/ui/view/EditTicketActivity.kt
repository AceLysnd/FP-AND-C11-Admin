package com.ace.c11flightadmin.ui.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.ace.c11flightadmin.databinding.ActivityEditTicketBinding
import com.ace.c11flightadmin.ui.viewmodel.EditTicketViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class EditTicketActivity : AppCompatActivity() {
    private var _binding: ActivityEditTicketBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EditTicketViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityEditTicketBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUserInfo()
        setOnclickListeners()
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
            binding.etFlightId.setText(it.data?.flightId.toString())
            binding.etType.setText(it.data?.type)
            binding.etPrice.setText(it.data?.price.toString())
            binding.etDesc.setText(it.data?.desc.toString())
        }
    }

    private fun setOnclickListeners() {
        binding.btnSave.setOnClickListener {
            createDialog()
        }
    }

    private fun createDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Update Ticket")
        builder.setMessage("Update this ticket?")

        builder.setPositiveButton(android.R.string.yes) { dialog, which ->

            val jsonObject = JSONObject()
            jsonObject.put("flightId", binding.etFlightId.text)
            jsonObject.put("type", binding.etType.text)
            jsonObject.put("price", binding.etPrice.text)
            jsonObject.put("desc", binding.etDesc.text)

            val jsonObjectString = jsonObject.toString()

            val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

            viewModel.updateTicket(requestBody)
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
                "Edit Success!", Toast.LENGTH_SHORT
            ).show()
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        builder.setNegativeButton(android.R.string.no) { dialog, which ->

        }
        builder.show()
    }

}
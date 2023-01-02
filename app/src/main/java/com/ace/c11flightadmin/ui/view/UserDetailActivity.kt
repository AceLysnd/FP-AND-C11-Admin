package com.ace.c11flightadmin.ui.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ace.c11flightadmin.R
import com.ace.c11flightadmin.databinding.ActivityUserDetailBinding
import com.ace.c11flightadmin.ui.adapter.TransactionHistoryAdapter
import com.ace.c11flightadmin.ui.view.UsersFragment.Companion.USER_ID
import com.ace.c11flightadmin.ui.viewmodel.UserDetailViewModel
import com.bumptech.glide.Glide

class UserDetailActivity : AppCompatActivity() {
    private var _binding: ActivityUserDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var transactionAdapter: TransactionHistoryAdapter
    private lateinit var transactionList: RecyclerView

    private val viewModel: UserDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        transactionList = this.findViewById(R.id.rv_transaction)
        transactionList.layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.VERTICAL, false
        )

        setUserId()
        setUserInfo()
        setOnclickListeners()

        setAdapter()
        observeData()
    }

    private fun setAdapter() {
        transactionAdapter = TransactionHistoryAdapter(arrayListOf(), arrayListOf()) {
//                ticket -> goToOrderDetails(ticket)
        }
        transactionList.adapter = transactionAdapter
    }

    private fun observeData() {

        viewModel.getTransactions()

//        viewModel.loadingState.observe(this) { isLoading ->
//            binding.pbPost.isVisible = isLoading
//            binding.rvTransaction.isVisible = !isLoading
//        }
//
//        viewModel.errorState.observe(this) { errorData ->
//            binding.tvError.isVisible = errorData.first
//            errorData.second?.message?.let {
//                binding.tvError.text = it
//            }
//        }

        viewModel.transactionResult.observe(this) {
            transactionAdapter.filter.filter(USER_ID.toString())
            transactionAdapter.addItem(it.data)
        }
    }

    private fun setUserId() {

    }

    private fun setUserInfo() {
        viewModel.getUserData()
        viewModel.loadingState.observe(this) { isLoading ->
            binding.pbPost.isVisible = isLoading
            binding.profileDetail.isVisible = !isLoading
        }

        viewModel.errorState.observe(this) { errorData ->
            binding.tvError.isVisible = errorData.first
            errorData.second?.message?.let {
                binding.tvError.text = it
            }
        }
        viewModel._dataResult.observe(this){
            binding.tvUsername.text = it.data?.username
            binding.tvEmail.text = it.data?.email
            binding.tvFirstName.text = it.data?.firstName.toString()
            binding.tvLastName.text = it.data?.lastName.toString()
            binding.tvAddress.text = it.data?.address.toString()
            binding.tvPhone.text = it.data?.phone.toString()
            if (it.data?.photo != null){
                val photoUrl = it.data.photo.replace("http","https")
                Glide.with(binding.imgProfile.context).load(photoUrl).into(binding.imgProfile)
            }
        }
    }

    private fun setOnclickListeners() {
        binding.btnDelete.setOnClickListener{
            createDialog()
        }

        binding.btnEditProfile.setOnClickListener {
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

            viewModel.deleteUser()
            viewModel.loadingState.observe(this) { isLoading ->
                binding.pbPost.isVisible = isLoading
            }

            viewModel.errorState.observe(this) { errorData ->
                binding.tvError.isVisible = errorData.first
                errorData.second?.message?.let {
                    binding.tvError.text = it
                }
            }
            Toast.makeText(applicationContext,
                "User deleted", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        builder.setNegativeButton(android.R.string.no) { dialog, which ->

        }
        builder.show()
    }

    companion object{
        var PHOTO_URL: String? = ""
    }
}
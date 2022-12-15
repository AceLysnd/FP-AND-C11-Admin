package com.ace.c11flightadmin.ui.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.ace.c11flightadmin.R
import com.ace.c11flightadmin.databinding.ActivityUserDetailBinding
import com.ace.c11flightadmin.ui.viewmodel.UserDetailViewModel
import com.bumptech.glide.Glide

class UserDetailActivity : AppCompatActivity() {
    private var _binding: ActivityUserDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: UserDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUserId()
        setUserInfo()
        setOnclickListeners()
    }

    private fun setUserId() {

    }

    private fun setUserInfo() {
        viewModel.getUserData()
        viewModel.loadingState.observe(this) { isLoading ->
            binding.pbPost.isVisible = isLoading
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
            PHOTO_URL = it.data?.photo

            //Unfixed
            if (it.data?.photo != null) {
                Glide.with(this)
                    .load("$PHOTO_URL")
                    .placeholder(R.drawable.ic_baseline_account_box_24)
                    .centerCrop()
                    .into(binding.imgProfile)


//                binding.imgProfile.load(photoUrl) {
//                    crossfade(true)
//                    placeholder(R.drawable.ic_baseline_account_box_24)
//                    transformations(RoundedCornersTransformation(10F))
//                }
            }
        }

    }

    private fun setOnclickListeners() {
        binding.btnDelete.setOnClickListener{
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }
    companion object{
        var PHOTO_URL: String? = ""
    }
}
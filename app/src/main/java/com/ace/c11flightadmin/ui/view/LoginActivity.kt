package com.ace.c11flightadmin.ui.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.ace.c11flightadmin.R
import com.ace.c11flightadmin.data.model.LoginInfo
import com.ace.c11flightadmin.data.services.ApiHelper
import com.ace.c11flightadmin.databinding.ActivityLoginBinding
import com.ace.c11flightadmin.ui.viewmodel.LoginActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.btnLogin.setOnClickListener { checkLogin() }
    }

    private fun checkLogin() {
        val apiService = ApiHelper()
        if (validateInput()) {

            val loginInfo = LoginInfo(
                status = "",
                id = null,
                username = "",
                email = binding.etUsername.text.toString(),
                password = binding.etPassword.text.toString()
            )

            apiService.loginUser(loginInfo) {
                if (it?.status == "OK") {
                    Toast.makeText(this, "login OK", Toast.LENGTH_SHORT).show()
                    saveLoginInfo(
                        it.username.toString(),
                        it.email.toString(),
                        it.password.toString(),
                        loginStatus = true
                    )
                    goToHome()
                } else {
                    Toast.makeText(this, "Email or password is not identified", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun validateInput(): Boolean {
        var isValid = true
        val username = binding.etUsername.text.toString()
        val password = binding.etPassword.text.toString()

        if (username.isEmpty()) {
            isValid = false
            binding.etUsername.error = getString(R.string.username_is_empty)
        }
        if (password.isEmpty()) {
            isValid = false
            binding.etPassword.error = getString(R.string.password_is_empty)
        }
        return isValid
    }

    fun saveLoginInfo(
        username: String,
        email: String,
        password: String,
        loginStatus: Boolean
    ) {
        viewModel.setAccount(username, email, password)
        viewModel.saveLoginStatus(loginStatus)
    }

    private fun goToHome() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    private var backButtonCount = 0
    override fun onBackPressed() {
        if (backButtonCount < 1) {
            Toast.makeText(this, getString(R.string.press_back_again), Toast.LENGTH_SHORT).show()
            backButtonCount += 1
        } else {
            moveTaskToBack(true)
            backButtonCount = 0
        }
    }

}
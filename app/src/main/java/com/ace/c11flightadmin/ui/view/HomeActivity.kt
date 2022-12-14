package com.ace.c11flightadmin.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import coil.imageLoader
import com.ace.c11flightadmin.R
import com.ace.c11flightadmin.databinding.ActivityHomeBinding
import com.ace.c11flightadmin.ui.viewmodel.HomeActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.MainScope

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private var _binding: ActivityHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        isLoginInfoValid()
        setOnClickListeners()
        setUsername()
        setBottomNav()
        binding.btnCristalitik.setOnClickListener {
            val intent = Intent(this, MainScope()::class.java)
            startActivity(intent)
            finish()

        }
    }
    private fun setUsername() {
        viewModel.getAccountPrefs().observe(this){
            TOKEN = it.token
        }
    }

    private fun isLoginInfoValid() {
        viewModel.getLoginStatus().observe(this) {
            if (it) {
                binding.messageLogin.visibility = View.GONE
                binding.flFragment.visibility = View.VISIBLE
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.profile -> {
                goToProfile()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun goToProfile() {
        val i = Intent(this, ProfileActivity::class.java)
        startActivity(i)
    }

    private fun setOnClickListeners() {

        binding.btnLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun setBottomNav() {
        val usersFragment = UsersFragment()
        val ticketFragment = TicketFragment()
        val createFragment = CreateFragment()
        setCurrentFragment(usersFragment)
        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.users -> {
                    setCurrentFragment(usersFragment)
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                R.id.tickets -> setCurrentFragment(ticketFragment)
                R.id.create -> setCurrentFragment(createFragment)
            }
            true
        }
    }

    private fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment,fragment)
            commit()
        }

    companion object {
        var TOKEN = ""
    }
}
package com.ace.c11flightadmin.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ace.c11flightadmin.data.model.Prefs
import com.ace.c11flightadmin.data.repository.LocalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeActivityViewModel @Inject constructor(
    private val repository: LocalRepository
) : ViewModel() {
    fun getLoginStatus(): LiveData<Boolean> {
        return repository.getLoginStatus()
    }

    fun getAccountPrefs(): LiveData<Prefs> {
        return repository.getAccountPrefs()
    }
}
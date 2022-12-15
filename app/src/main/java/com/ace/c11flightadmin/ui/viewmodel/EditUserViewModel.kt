package com.ace.c11flightadmin.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ace.c11flightadmin.data.model.AccountResponse
import com.ace.c11flightadmin.data.services.AccountApiService
import com.ace.c11flightadmin.ui.view.UsersFragment.Companion.USER_ID
import kotlinx.coroutines.launch
import okhttp3.RequestBody

class EditUserViewModel  : ViewModel() {

    private val apiService : AccountApiService by lazy {
        AccountApiService.invoke()
    }

    val _dataResult = MutableLiveData<AccountResponse>()
    val dataResult: LiveData<AccountResponse>
        get() =_dataResult

    val loadingState = MutableLiveData<Boolean>()
    val errorState = MutableLiveData<Pair<Boolean, Exception?>>()

    fun getUserData() {
        loadingState.postValue(true)
        errorState.postValue(Pair(false, null))
        viewModelScope.launch {
            try {
                val data = apiService.getUserById(USER_ID)
                viewModelScope.launch {
                    _dataResult.postValue(data)
                    loadingState.postValue(false)
                    errorState.postValue(Pair(false,null))
                }
            } catch (e: Exception) {
                viewModelScope.launch {
                    loadingState.postValue(false)
                    errorState.postValue(Pair(true,e))
                }
            }
        }
    }

    fun updateUser(data: RequestBody) {
        loadingState.postValue(true)
        errorState.postValue(Pair(false, null))
        viewModelScope.launch {
            try {
                apiService.updateUserById(USER_ID, data)
                viewModelScope.launch {
                    loadingState.postValue(false)
                    errorState.postValue(Pair(false,null))
                }
            } catch (e: Exception) {
                viewModelScope.launch {
                    loadingState.postValue(false)
                    errorState.postValue(Pair(true,e))
                }
            }
        }
    }
}
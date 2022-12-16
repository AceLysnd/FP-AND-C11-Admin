package com.ace.c11flightadmin.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ace.c11flightadmin.data.model.Account
import com.ace.c11flightadmin.data.model.TicketListResponse
import com.ace.c11flightadmin.data.model.UserResponse
import com.ace.c11flightadmin.data.services.AccountApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TicketFragmentViewModel : ViewModel() {

    private val apiService : AccountApiService by lazy {
        AccountApiService.invoke()
    }

    val _ticketResult = MutableLiveData<TicketListResponse>()
    val ticketResult: LiveData<TicketListResponse>
        get() =_ticketResult

    val loadingState = MutableLiveData<Boolean>()
    val errorState = MutableLiveData<Pair<Boolean, Exception?>>()

    fun getTickets() {
        loadingState.postValue(true)
        errorState.postValue(Pair(false, null))
        viewModelScope.launch {
            try {
                val data = apiService.getTickets()
                viewModelScope.launch {
                    _ticketResult.postValue(data)
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
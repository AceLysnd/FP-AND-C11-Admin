package com.ace.c11flightadmin.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ace.c11flightadmin.data.model.AccountResponse
import com.ace.c11flightadmin.data.model.TicketResponse
import com.ace.c11flightadmin.data.services.AccountApiService
import com.ace.c11flightadmin.ui.view.TicketFragment
import com.ace.c11flightadmin.ui.view.TicketFragment.Companion.TICKET_ID
import com.ace.c11flightadmin.ui.view.UsersFragment.Companion.USER_ID
import kotlinx.coroutines.launch
import okhttp3.RequestBody

class CreateTicketViewModel  : ViewModel() {

    private val apiService : AccountApiService by lazy {
        AccountApiService.invoke()
    }

    val _ticketResult = MutableLiveData<TicketResponse>()
    val ticketResult: LiveData<TicketResponse>
        get() =_ticketResult

    val loadingState = MutableLiveData<Boolean>()
    val errorState = MutableLiveData<Pair<Boolean, Exception?>>()

    fun createTicket(data: RequestBody) {
        loadingState.postValue(true)
        errorState.postValue(Pair(false, null))
        viewModelScope.launch {
            try {
                apiService.createTicket(data)
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
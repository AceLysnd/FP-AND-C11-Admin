package com.ace.c11flightadmin.data.model


import com.google.gson.annotations.SerializedName

data class TicketResponse(
    @SerializedName("data")
    val data: Ticket?,
    @SerializedName("status")
    val status: String?
)
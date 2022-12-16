package com.ace.c11flightadmin.data.model


import com.google.gson.annotations.SerializedName

data class Ticket(
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("desc")
    val desc: String?,
    @SerializedName("flight_id")
    val flightId: Int?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("price")
    val price: Int?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("updatedAt")
    val updatedAt: String?
)
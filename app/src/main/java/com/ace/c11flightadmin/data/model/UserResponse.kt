package com.ace.c11flightadmin.data.model

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("data")
    val data: Data,
    @SerializedName("status")
    val status: String?,
    @SerializedName("total")
    val total: Total?
)

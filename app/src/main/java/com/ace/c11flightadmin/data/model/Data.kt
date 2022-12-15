package com.ace.c11flightadmin.data.model


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("users")
    val users: List<Account>?
)
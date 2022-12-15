package com.ace.c11flightadmin.data.model

import com.google.gson.annotations.SerializedName

data class AccountResponse(
    @SerializedName("status") val status: String?,
    @SerializedName("data") val data: Account?
)

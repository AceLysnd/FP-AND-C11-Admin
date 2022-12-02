package com.ace.c11flightadmin.data.model

import com.google.gson.annotations.SerializedName

data class UserInfo(
    @SerializedName("status") val status: String?,
    @SerializedName("id") val id:Int?,
    @SerializedName("username") val username: String?,
    @SerializedName("email") val email: String?,
    @SerializedName("password") val password: String?
)

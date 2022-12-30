package com.ace.c11flightadmin.data.model


import com.google.gson.annotations.SerializedName

data class Notification(
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("isRead")
    val isRead: Boolean?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("transaction_id")
    val transactionId: Int?,
    @SerializedName("updatedAt")
    val updatedAt: String?,
    @SerializedName("user_id")
    val userId: Int?
)
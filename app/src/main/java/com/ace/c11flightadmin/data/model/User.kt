package com.ace.c11flightadmin.data.model


import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("address")
    val address: String?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("firstName")
    val firstName: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("imgPassport")
    val imgPassport: Any?,
    @SerializedName("imgResidentPermit")
    val imgResidentPermit: Any?,
    @SerializedName("imgVisa")
    val imgVisa: Any?,
    @SerializedName("lastName")
    val lastName: String?,
    @SerializedName("password")
    val password: String?,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("photo")
    val photo: String?,
    @SerializedName("role")
    val role: String?,
    @SerializedName("updatedAt")
    val updatedAt: String?,
    @SerializedName("username")
    val username: String?
)
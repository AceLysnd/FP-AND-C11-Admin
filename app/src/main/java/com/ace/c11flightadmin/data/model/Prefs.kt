package com.ace.c11flightadmin.data.model

data class Prefs(
    val accountId: Long,
    val username: String,
    val email: String,
    val password: String,
    val loginStatus: Boolean,
    val profilePicture: String,
    val token: String
)

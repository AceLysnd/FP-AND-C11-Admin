package com.ace.c11flightadmin.data.services

import com.ace.c11flightadmin.data.model.LoginInfo
import com.ace.c11flightadmin.data.model.UserInfo
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AccountApiService {

    @Headers("Content-Type: application/json")
    @POST("register")
    fun registerUser(
        @Body userData: UserInfo
    ): Call<UserInfo>

    @POST("login")
    fun loginUser(
        @Body loginData: LoginInfo
    ): Call<LoginInfo>
}
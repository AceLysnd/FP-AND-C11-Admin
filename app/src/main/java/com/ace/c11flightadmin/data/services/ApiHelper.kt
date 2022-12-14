package com.ace.c11flightadmin.data.services

import com.ace.c11flightadmin.data.model.LoginInfo
import com.ace.c11flightadmin.data.model.UserInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiHelper {

    fun loginUser(loginData: LoginInfo, onResult: (LoginInfo?) -> Unit){
        val retrofit = ServiceBuilder.buildService(AccountApiService::class.java)
        retrofit.loginUser(loginData).enqueue(
            object : Callback<LoginInfo> {
                override fun onFailure(call: Call<LoginInfo>, t: Throwable) {
                    onResult(null)
                }
                override fun onResponse( call: Call<LoginInfo>, response: Response<LoginInfo>) {
                    val addedUser = response.body()
                    onResult(addedUser)
                }
            }
        )
    }
}
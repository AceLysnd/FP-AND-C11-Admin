package com.ace.c11flightadmin.data.services

import com.ace.c11flightadmin.data.model.AccountResponse
import com.ace.c11flightadmin.data.model.LoginInfo
import com.ace.c11flightadmin.data.model.UserInfo
import com.ace.c11flightadmin.data.model.UserResponse
import com.ace.c11flightadmin.data.services.ServiceBuilder.BASE_URL
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

interface AccountApiService {

    @POST("login")
    fun loginUser(
        @Body loginData: LoginInfo
    ): Call<LoginInfo>

    @Headers("Content-Type: application/json")
    @GET("users")
    suspend fun getUsers(
    ): UserResponse

    @GET("users/{id}")
    suspend fun getUserById(
        @Path("id") id: Int,
    ): AccountResponse

    @DELETE("users/{id}")
    suspend fun deleteUserById(
        @Path("id") id: Int,
    ): AccountResponse

    companion object{

        @JvmStatic
        operator fun invoke() : AccountApiService{
            val authInterceptor = Interceptor{
                val originRequest = it.request()
                val newUrl = originRequest.url.newBuilder().apply {
                }.build()
                it.proceed(originRequest.newBuilder().url(newUrl).build())
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(authInterceptor)
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
                .create(AccountApiService::class.java)
        }
    }

}
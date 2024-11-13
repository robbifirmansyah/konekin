package com.example.pertemuan11_konekin.model

import com.google.gson.annotations.SerializedName
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

data class Data(
    @SerializedName("id")
    val id: Int,

    @SerializedName("first_name")
    val employeeName: String,

    @SerializedName("email")
    val employeeSalary: String,

    @SerializedName("last_name")
    val employeeAge: String,

    @SerializedName("avatar")
    val profileImage: String
)



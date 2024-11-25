package com.example.lovecalculator.data.network

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import retrofit2.Call

interface ApiService {
    @GET("getPercentage")
    fun getPercentage(
        @Query("fname") firstName: String,
        @Query("sname") secondName: String,
        @Header("x-rapidapi-key") key: String,
        @Header("x-rapidapi-host") host: String
    ): Call<LoveModel>
}
package com.example.lovecalculator.data.network

import com.google.gson.annotations.SerializedName

data class LoveModel(
    @SerializedName("fname")
    val firstName: String,
    @SerializedName("sname")
    val secondName: String,
    val percentage: Int,
    val result: String
)

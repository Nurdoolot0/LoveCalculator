package com.example.lovecalculator.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history")
data class LoveResultEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val firstName: String,
    val secondName: String,
    val percentage: Int,
    val result: String,
)

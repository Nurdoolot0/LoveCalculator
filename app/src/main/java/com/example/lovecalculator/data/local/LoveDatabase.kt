package com.example.lovecalculator.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [LoveResultEntity::class], version = 1)
abstract class LoveResultDatabase : RoomDatabase() {

    abstract fun loveResultDao(): LoveResultDao

    }

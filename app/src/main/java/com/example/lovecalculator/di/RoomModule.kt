package com.example.lovecalculator.di

import android.content.Context
import androidx.room.Room
import com.example.lovecalculator.data.local.LoveResultDao
import com.example.lovecalculator.data.local.LoveResultDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): LoveResultDatabase {
        return Room.databaseBuilder(
            context,
            LoveResultDatabase::class.java,
            "love_result_database"
        )
            .build()
    }

    @Provides
    @Singleton
    fun provideLoveResultDao(database: LoveResultDatabase): LoveResultDao {
        return database.loveResultDao()
    }
}


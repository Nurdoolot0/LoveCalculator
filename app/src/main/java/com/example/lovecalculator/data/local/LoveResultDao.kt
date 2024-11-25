package com.example.lovecalculator.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface LoveResultDao {

    @Insert
    suspend fun insert(loveResult: LoveResultEntity)

    @Delete
    suspend fun delete(loveResult: LoveResultEntity)

    // Запрос для извлечения данных по алфавиту
    @Query("SELECT * FROM history ORDER BY firstName ASC")
    fun getAllResultsSortedByName(): LiveData<List<LoveResultEntity>>

    @Query("SELECT * FROM history WHERE percentage > :minPercentage ORDER BY percentage DESC")
    fun getResultsWithHighPercentage(minPercentage: Int): LiveData<List<LoveResultEntity>>

    @Query("SELECT AVG(percentage) FROM history WHERE firstName = :firstName")
    suspend fun getAveragePercentage(firstName: String): Double?

    @Query("""
    SELECT firstName, COUNT(*) as count 
    FROM history 
    GROUP BY firstName 
    ORDER BY count DESC 
    LIMIT 5
""")
    fun getMostFrequentFirstNames(): LiveData<List<FrequentName>>

    // Получить запись с максимальным процентом любви
    @Query("SELECT * FROM history ORDER BY percentage DESC LIMIT 1")
    suspend fun getMaxLoveResult(): LoveResultEntity

}


package com.example.lovecalculator.ui

import androidx.lifecycle.LiveData
import com.example.lovecalculator.data.local.FrequentName
import com.example.lovecalculator.data.local.LoveResultDao
import com.example.lovecalculator.data.local.LoveResultEntity
import javax.inject.Inject

class LoveResultRepository @Inject constructor(private val loveResultDao: LoveResultDao) {

    suspend fun getAveragePercentage(firstName: String): Double? {
        return loveResultDao.getAveragePercentage(firstName)
    }
    fun getMostFrequentFirstNames(): LiveData<List<FrequentName>> {
        return loveResultDao.getMostFrequentFirstNames()
    }
    suspend fun getMaxLoveResult(): LoveResultEntity {
        return loveResultDao.getMaxLoveResult()
    }
}


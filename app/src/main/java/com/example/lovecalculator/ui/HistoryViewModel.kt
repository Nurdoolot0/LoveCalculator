package com.example.lovecalculator.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lovecalculator.data.local.FrequentName
import com.example.lovecalculator.data.local.LoveResultEntity
import com.example.lovecalculator.data.local.LoveResultDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val repository: LoveResultRepository,
    private val loveResultDao: LoveResultDao// Dependency Injection
): ViewModel() {

    val historyList: LiveData<List<LoveResultEntity>> = loveResultDao.getAllResultsSortedByName()
    private val _averagePercentage = MutableLiveData<Double?>()
    val mostFrequentNames: LiveData<List<FrequentName>> = repository.getMostFrequentFirstNames()
    private val _maxLoveResult = MutableLiveData<LoveResultEntity?>()
    val maxLoveResult: LiveData<LoveResultEntity?> = _maxLoveResult

    fun getMaxLoveResult() {
        viewModelScope.launch {
            val result = repository.getMaxLoveResult()
            _maxLoveResult.value = result
        }
    }

        fun getAveragePercentage(firstName: String) {
            viewModelScope.launch {
                val average = repository.getAveragePercentage(firstName)
                Log.d("HistoryViewModel", "Average for $firstName: $average")
                _averagePercentage.value = average
            }
        }

        fun deleteHistoryItem(item: LoveResultEntity) {
            viewModelScope.launch {
                loveResultDao.delete(item)
            }
        }

        fun insertResult(loveResult: LoveResultEntity) {
            viewModelScope.launch {
                loveResultDao.insert(loveResult)
            }
        }
    }


package com.example.lovecalculator.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lovecalculator.data.LoveModel
import com.example.lovecalculator.data.ApiService
import com.example.lovecalculator.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor(
    private val apiService: ApiService
) : ViewModel() {

    private val _loveResult = MutableLiveData<LoveModel>()
    val loveResult: LiveData<LoveModel> get() = _loveResult

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun calculateLovePercentage(firstName: String, secondName: String) {
        if (firstName.isEmpty() || secondName.isEmpty()) {
            _error.value = "Оба имени должны быть заполнены"
            return
        }

        _loading.value = true

        apiService.getPercentage(
            firstName,
            secondName,
            Constants.API_KEY,
            Constants.HOST
        ).enqueue(object : Callback<LoveModel> {
            override fun onResponse(call: Call<LoveModel>, response: Response<LoveModel>) {
                _loading.value = false
                if (response.isSuccessful) {
                    response.body()?.let {
                        _loveResult.value = it
                    } ?: run {
                        _error.value = "Ошибка получения данных"
                    }
                } else {
                    _error.value = "Код ошибки: ${response.code()}"
                }
            }

            override fun onFailure(call: Call<LoveModel>, t: Throwable) {
                _loading.value = false
                _error.value = "Ошибка сети: ${t.localizedMessage}"
            }
        })
    }
}

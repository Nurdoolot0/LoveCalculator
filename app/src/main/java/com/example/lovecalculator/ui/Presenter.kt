package com.example.lovecalculator.ui

import com.example.lovecalculator.data.ApiService
import com.example.lovecalculator.data.LoveModel
import com.example.lovecalculator.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Presenter(private val view: Contract.View, private val apiService: ApiService) : Contract.Presenter {

    override fun calculateLovePercentage(firstName: String, secondName: String) {
        if (firstName.isEmpty() || secondName.isEmpty()) {
            view.showError("Оба имени должны быть заполнены")
            return
        }

        view.showLoading()

        apiService.getPercentage(
            firstName,
            secondName,
            host = Constants.HOST,
            key = Constants.API_KEY
        ).enqueue(object : Callback<LoveModel> {

            override fun onResponse(call: Call<LoveModel>, response: Response<LoveModel>) {
                view.hideLoading()
                if (response.isSuccessful) {
                    response.body()?.let {
                        view.showResult(it)
                    } ?: view.showError("Ошибка получения данных")
                } else {
                    view.showError("Не удалось получить результат")
                }
            }

            override fun onFailure(call: Call<LoveModel>, t: Throwable) {
                view.hideLoading()
                view.showError("Ошибка сети: ${t.message}")
            }
        })
    }
}
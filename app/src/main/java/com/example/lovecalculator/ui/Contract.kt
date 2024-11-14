package com.example.lovecalculator.ui

import com.example.lovecalculator.data.LoveModel

interface Contract {
    interface View {
        fun showLoading()
        fun hideLoading()
        fun showResult(result: LoveModel)
        fun showError(message: String)
    }

    interface Presenter {
        fun calculateLovePercentage(firstName: String, secondName: String)
    }
}
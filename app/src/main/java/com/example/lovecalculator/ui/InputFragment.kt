package com.example.lovecalculator.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.lovecalculator.data.LoveModel
import com.example.lovecalculator.data.RetrofitInstance
import com.example.lovecalculator.databinding.FragmentInputBinding

class InputFragment : Fragment(), Contract.View {

    private lateinit var binding: FragmentInputBinding
    private lateinit var presenter: Contract.Presenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInputBinding.inflate(inflater, container, false)
        presenter = Presenter(this, RetrofitInstance.api)

        setupUI()
        return binding.root
    }

    private fun setupUI() {
        binding.btnCalculate.setOnClickListener {
            val firstName = binding.etFirstName.text.toString().trim()
            val secondName = binding.etSecondName.text.toString().trim()
            presenter.calculateLovePercentage(firstName, secondName)
        }
    }

    override fun showLoading() {
        binding.loadingAnimation.visibility = View.VISIBLE
        binding.btnCalculate.isEnabled = false
    }


    override fun hideLoading() {
        binding.loadingAnimation.visibility = View.GONE
        binding.btnCalculate.isEnabled = true
    }

    override fun showResult(result: LoveModel) {
        val action = InputFragmentDirections.actionInputFragmentToResultFragment(
            result.firstName,
            result.secondName,
            result.percentage,
            result.result
        )
        findNavController().navigate(action)
        binding.etFirstName.text.clear()
        binding.etSecondName.text.clear()
    }

    override fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
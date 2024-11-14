package com.example.lovecalculator.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.lovecalculator.data.LoveModel
import com.example.lovecalculator.data.RetrofitInstance
import com.example.lovecalculator.databinding.FragmentInputBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
        binding.etFirstName.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.etFirstName.hint = ""
            } else {
                binding.etFirstName.hint = "Введите имя"
            }
        }

        binding.etSecondName.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.etSecondName.hint = ""
            } else {
                binding.etSecondName.hint = "Введите имя"
            }
        }

        binding.btnCalculate.setOnClickListener {
            val firstName = binding.etFirstName.text.toString().trim()
            val secondName = binding.etSecondName.text.toString().trim()
            presenter.calculateLovePercentage(firstName, secondName)
        }
    }


    override fun showLoading() {
            binding.loadingAnimation.visibility = View.VISIBLE
        }


    override fun hideLoading() {
        binding.loadingAnimation.visibility = View.GONE
        binding.btnCalculate.isEnabled = true
    }

    override fun showResult(result: LoveModel) {
            lifecycleScope.launch {
                binding.loadingAnimation.visibility = View.VISIBLE
                binding.loadingAnimation.playAnimation()

                delay(4000)
                val action = InputFragmentDirections.actionInputFragmentToResultFragment(
                    result.firstName,
                    result.secondName,
                    result.percentage,
                    result.result
                )
                findNavController().navigate(action)
            }
    }

    override fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onStop() {
        super.onStop()
        binding.etFirstName.text.clear()
        binding.etSecondName.text.clear()
    }
}
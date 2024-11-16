package com.example.lovecalculator.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.lovecalculator.databinding.FragmentInputBinding

class InputFragment : Fragment() {

    private lateinit var binding: FragmentInputBinding
    private val viewModel: LoveViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInputBinding.inflate(inflater, container, false)

        setupUI()
        observeViewModel()

        return binding.root
    }

    private fun setupUI() {
        binding.btnCalculate.setOnClickListener {
            val firstName = binding.etFirstName.text.toString().trim()
            val secondName = binding.etSecondName.text.toString().trim()
            viewModel.calculateLovePercentage(firstName, secondName)
        }
    }

    private fun observeViewModel() {
        viewModel.loading.observe(viewLifecycleOwner, Observer { isLoading ->
            binding.loadingAnimation.visibility = if (isLoading) View.VISIBLE else View.GONE
            binding.btnCalculate.isEnabled = !isLoading
        })

        viewModel.loveResult.observe(viewLifecycleOwner, Observer { result ->
            val action = InputFragmentDirections.actionInputFragmentToResultFragment(
                result.firstName,
                result.secondName,
                result.percentage,
                result.result
            )
            findNavController().navigate(action)
        })

        viewModel.error.observe(viewLifecycleOwner, Observer { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        })
    }
}
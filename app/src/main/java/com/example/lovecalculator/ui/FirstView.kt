package com.example.lovecalculator.ui

import android.os.Bundle
import android.os.Handler
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.lovecalculator.databinding.FragmentInputBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FirstView : Fragment() {

    private lateinit var binding: FragmentInputBinding
    private val viewModel: ViewModel by viewModels()

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

            binding.loadingAnimation.visibility = View.VISIBLE

            makeElementsTransparent()

            viewModel.calculateLovePercentage(firstName, secondName)
        }

        binding.etFirstName.setOnEditorActionListener { _, actionId, event ->
            if (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER || actionId == EditorInfo.IME_ACTION_DONE) {
                binding.etSecondName.requestFocus()
                return@setOnEditorActionListener true
            }
            false
        }

        binding.etSecondName.setOnEditorActionListener { _, actionId, event ->
            if (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER || actionId == EditorInfo.IME_ACTION_DONE) {
                return@setOnEditorActionListener true
            }
            false
        }
    }

    private fun makeElementsTransparent() {
        binding.btnCalculate.alpha = 0f
        binding.etFirstName.alpha = 0f
        binding.etSecondName.alpha = 0f
        binding.tvFn.alpha = 0f
        binding.tvSn.alpha = 0f
        binding.tvLc.alpha = 0f
        binding.imageHt.alpha = 0f

        Handler().postDelayed({
            binding.imageHt.visibility = View.INVISIBLE
            binding.btnCalculate.visibility = View.INVISIBLE
            binding.etFirstName.visibility = View.INVISIBLE
            binding.etSecondName.visibility = View.INVISIBLE
            binding.tvFn.visibility = View.INVISIBLE
            binding.tvSn.visibility = View.INVISIBLE
            binding.tvLc.visibility = View.INVISIBLE

            Handler().postDelayed({
                navigateToSecondScreen()
            }, 3500)
        }, 1000)
    }

    private fun navigateToSecondScreen() {
        val firstName = binding.etFirstName.text.toString().trim()
        val secondName = binding.etSecondName.text.toString().trim()
        val action = FirstViewDirections.actionInputFragmentToResultFragment(
            firstName,
            secondName,
            0,
            ""
        )
        findNavController().navigate(action)
    }

    private fun observeViewModel() {
        viewModel.loading.observe(viewLifecycleOwner, Observer { isLoading ->
            binding.loadingAnimation.visibility = View.VISIBLE
            binding.btnCalculate.isEnabled = !isLoading
        })

        viewModel.loveResult.observe(viewLifecycleOwner, Observer { result ->
        })

        viewModel.error.observe(viewLifecycleOwner, Observer { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        })
    }
}

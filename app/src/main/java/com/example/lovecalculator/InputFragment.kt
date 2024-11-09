package com.example.lovecalculator

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.lovecalculator.databinding.FragmentInputBinding
import okhttp3.Response
import retrofit2.Call
import retrofit2.Callback

class InputFragment : Fragment() {

    private var _binding: FragmentInputBinding? = null
    private val binding get() = _binding!!
    private val api = RetrofitInstance.api

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInputBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCalculate.setOnClickListener {
            val firstName = binding.etFirstName.text.toString().trim()
            val secondName = binding.etSecondName.text.toString().trim()
            binding.loadingAnimation.visibility = View.VISIBLE
            binding.loadingAnimation.playAnimation()

            if (firstName.isEmpty() || secondName.isEmpty()) {
                Toast.makeText(requireContext(), "Please enter both names", Toast.LENGTH_SHORT)
                    .show()
                binding.loadingAnimation.visibility = View.GONE
                return@setOnClickListener
            }else{
                binding.loadingAnimation.visibility = View.VISIBLE
            }

            api.getPercentage(
                firstName = firstName,
                secondName = secondName,
                key = "d63455e580mshff4c05fa8fbdd69p18f514jsndaad0fd1e31f",
                host = "love-calculator.p.rapidapi.com"
            ).enqueue(object : Callback<LoveModel> {
                override fun onResponse(
                    call: Call<LoveModel>,
                    response: retrofit2.Response<LoveModel>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        val loveModel = response.body()!!
                        val action = InputFragmentDirections.actionInputFragmentToResultFragment(
                            firstName = loveModel.firstName,
                            secondName = loveModel.secondName,
                            percentage = loveModel.percentage,
                            result = loveModel.result
                        )
                        findNavController().navigate(action)
                    } else {
                        Toast.makeText(requireContext(), "Error in response", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                override fun onFailure(call: Call<LoveModel>, t: Throwable) {
                    Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

}


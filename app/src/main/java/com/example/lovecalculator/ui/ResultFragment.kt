package com.example.lovecalculator.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.lovecalculator.databinding.FragmentInputBinding
import com.example.lovecalculator.databinding.FragmentResultBinding

class ResultFragment : Fragment() {

    private lateinit var binding: FragmentResultBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentResultBinding.inflate(inflater, container, false)

        val args = arguments ?: return binding.root
        val firstName = args.getString("firstName")
        val secondName = args.getString("secondName")
        val percentage = args.getInt("percentage")
        val result = args.getString("result")

        binding.tvNames.text = "$firstName ❤ $secondName"
        binding.tvPercentage.text = "$percentage%"
        binding.tvResultComment.text = result
        binding.btnTryAgain.setOnClickListener {
            findNavController().navigateUp()
        }

        return binding.root
    }
}
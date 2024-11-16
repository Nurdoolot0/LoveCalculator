package com.example.lovecalculator.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.lovecalculator.databinding.FragmentResultBinding

class ResultFragment : Fragment() {

    private lateinit var binding: FragmentResultBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentResultBinding.inflate(inflater, container, false)

        val args = ResultFragmentArgs.fromBundle(requireArguments())
        binding.tvNames.text = "${args.firstName} ❤ ${args.secondName}"
        binding.tvPercentage.text = "${args.percentage}%"
        binding.tvResultComment.text = args.result

        binding.btnTryAgain.setOnClickListener {
            val action = ResultFragmentDirections.actionResultFragmentToInputFragment()
            findNavController().navigate(action)
        }


        return binding.root
    }
}

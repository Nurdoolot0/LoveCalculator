package com.example.lovecalculator.ui

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lovecalculator.data.local.LoveResultDao
import com.example.lovecalculator.data.local.LoveResultEntity
import com.example.lovecalculator.databinding.FragmentHistoryBinding
import com.example.lovecalculator.ui.HistoryAdapter
import com.example.lovecalculator.ui.HistoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HistoryViewModel by viewModels()
    private var historyAdapter = HistoryAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        viewModel.historyList.observe(viewLifecycleOwner) { history ->
            historyAdapter.submitList(history)
        }
        viewModel.getAveragePercentage("John")

        viewModel.mostFrequentNames.observe(viewLifecycleOwner) { names ->
            names.forEach { nameWithCount ->
                Log.d(
                    "HistoryFragment",
                    "Имя: ${nameWithCount.firstName}, количество: ${nameWithCount.count}"
                )
            }
        }

        viewModel.maxLoveResult.observe(viewLifecycleOwner) { result ->
            if (result != null) {
                binding.textViewMaxLove.text = "Имя: ${result.firstName}❤${result.secondName} Процент: ${result.percentage}% (Самый высокий %)"
            } else {
                binding.textViewMaxLove.text = "Нет данных"
            }
        }
        viewModel.getMaxLoveResult()
    }


        private fun setupRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = historyAdapter
        }

        historyAdapter.setOnItemLongClickListener { loveResult ->
            showDeleteConfirmationDialog(loveResult)
        }
    }

    private fun showDeleteConfirmationDialog(loveResult: LoveResultEntity) {
        AlertDialog.Builder(requireContext())
            .setTitle("Удалить запись?")
            .setMessage("Вы уверены, что хотите удалить эту запись?")
            .setPositiveButton("Удалить") { _, _ ->
                viewModel.deleteHistoryItem(loveResult)
                Toast.makeText(requireContext(), "Запись удалена", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Отмена", null)
            .show()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

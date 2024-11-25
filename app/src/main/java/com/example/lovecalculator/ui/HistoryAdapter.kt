package com.example.lovecalculator.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lovecalculator.data.local.LoveResultEntity
import com.example.lovecalculator.databinding.ItemHistoryBinding

class HistoryAdapter : ListAdapter<LoveResultEntity, HistoryAdapter.HistoryViewHolder>(DiffCallback()) {

    private var onItemLongClickListener: ((LoveResultEntity) -> Unit)? = null

    fun setOnItemLongClickListener(listener: (LoveResultEntity) -> Unit) {
        onItemLongClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class HistoryViewHolder(private val binding: ItemHistoryBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnLongClickListener {
                onItemLongClickListener?.invoke(getItem(adapterPosition))
                true
            }
        }

        fun bind(item: LoveResultEntity) {
            binding.apply {
                firstName.text = item.firstName
                secondName.text = item.secondName
                percentage.text = "${item.percentage}%"
                result.text = item.result
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<LoveResultEntity>() {
        override fun areItemsTheSame(oldItem: LoveResultEntity, newItem: LoveResultEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: LoveResultEntity, newItem: LoveResultEntity): Boolean {
            return oldItem == newItem
        }
    }
}

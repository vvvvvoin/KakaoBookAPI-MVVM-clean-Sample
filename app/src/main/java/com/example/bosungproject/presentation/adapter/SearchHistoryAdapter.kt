package com.example.bosungproject.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bosungproject.data.entity.SearchHistory
import com.example.bosungproject.databinding.SearchHistoryItemBinding
import com.example.bosungproject.presentation.ui.viewModel.SearchViewModel

class  SearchHistoryAdapter(private val viewModel: SearchViewModel): RecyclerView.Adapter<SearchHistoryAdapter.SearchHistoryHolder>() {

    var historyList = ArrayList<SearchHistory>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        SearchHistoryHolder(SearchHistoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            .apply {
                viewModel = this@SearchHistoryAdapter.viewModel
            })


    override fun onBindViewHolder(holder: SearchHistoryHolder, position: Int) {
        holder.bind(historyList[position])
    }

    override fun getItemCount() = historyList.size

    inner class SearchHistoryHolder(private val binding: SearchHistoryItemBinding) : RecyclerView.ViewHolder(binding.root){
        var layout = binding.searchHistoryLayout

        fun bind(item : SearchHistory){
            binding.item = item
        }
    }
}
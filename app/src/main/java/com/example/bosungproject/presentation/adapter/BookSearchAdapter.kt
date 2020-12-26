package com.example.bosungproject.presentation.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.example.bosungproject.R
import com.example.bosungproject.databinding.SearchItemBinding
import com.example.bosungproject.domain.model.KakaoSearchSortEnum
import com.example.bosungproject.domain.model.SearchData
import com.example.bosungproject.domain.model.SearchQuery
import com.example.bosungproject.presentation.ui.viewModel.SearchViewModel

class BookSearchAdapter(private val viewModel : SearchViewModel): RecyclerView.Adapter<BookSearchAdapter.ItemHolder>(){

    var searchData = ArrayList<SearchData.Documents>()
    var query = viewModel.searchQuery.value
    var moreLoad = false
    var pageable_count = 0

    private lateinit var  itemClickListener: ItemClickListener
    interface ItemClickListener  {
        fun onClick(view: View, position: Int, vo: SearchData.Documents)
    }

    fun setItemClickListener(itemClickListener: ItemClickListener){
        this.itemClickListener = itemClickListener
    }

    override fun getItemCount(): Int {
        return searchData.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=
        ItemHolder(SearchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))


    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        if(position > searchData.size - 4 && moreLoad){
/*            viewModel.search(SearchQuery(query, KakaoSearchSortEnum.Accuracy, ))*/
        }

        onItemClickListener.let {
            holder.holderLayout.setOnClickListener {
              onItemClickListener?.onClick(it, position, searchData[position])
            }
        }
        holder.bind(searchData[position])
    }

    inner class ItemHolder(private val binding : SearchItemBinding) : RecyclerView.ViewHolder(binding.root){
        val bookImage : ImageView = binding.bookImage
        val holderLayout = binding.searchItemLayout

        fun bind(item: SearchData.Documents) {
            bookImage.run {
                Glide.with(context).load(item.thumbnail).override(Target.SIZE_ORIGINAL).into(this)
            }
            binding.item = item
        }
    }

    var onItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onClick(
            view: View,
            position: Int,
            data: SearchData.Documents
        )
    }

    fun onItemClick(listener: (view: View, position: Int, data: SearchData.Documents) -> Unit) {
        onItemClickListener = object : OnItemClickListener {
            override fun onClick(view: View, position: Int, data: SearchData.Documents) {
                listener(view, position, data)
            }
        }
    }

}
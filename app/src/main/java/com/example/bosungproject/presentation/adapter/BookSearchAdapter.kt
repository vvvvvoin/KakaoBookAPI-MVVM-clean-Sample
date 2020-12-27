package com.example.bosungproject.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.example.bosungproject.databinding.SearchItemBinding
import com.example.bosungproject.domain.model.KakaoSearchSortEnum
import com.example.bosungproject.domain.model.SearchData
import com.example.bosungproject.domain.model.SearchQuery
import com.example.bosungproject.presentation.ui.viewModel.SearchViewModel

class BookSearchAdapter(private val viewModel : SearchViewModel): RecyclerView.Adapter<BookSearchAdapter.ItemHolder>(){

    var searchList = ArrayList<SearchData.Documents>()
    var query = ""
    var moreLoad = false
    var page = 0

    private lateinit var  itemClickListener: ItemClickListener
    interface ItemClickListener  {
        fun onClick(view: View, position: Int, vo: SearchData.Documents)
    }

    fun setItemClickListener(itemClickListener: ItemClickListener){
        this.itemClickListener = itemClickListener
    }

    override fun getItemCount(): Int {
        return searchList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=
        ItemHolder(SearchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))


    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        if (position > searchList.size - 4 && moreLoad && page != -1) {
/*            viewModel.search(SearchQuery(query, KakaoSearchSortEnum.Accuracy, ))*/
            moreLoad = false
            viewModel.search(SearchQuery(query!!, KakaoSearchSortEnum.Accuracy, page, 10))
        }

        onItemClickListener.let {
            holder.holderLayout.setOnClickListener {
              onItemClickListener?.onClick(it, position, searchList[position])
            }
        }
        holder.bind(searchList[position])
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
package com.example.bosungproject.presentation.adapter

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
import com.example.bosungproject.domain.model.SearchData
import com.example.bosungproject.presentation.ui.viewModel.SearchViewModel

class BookSearchAdapter(private val viewModel : SearchViewModel): RecyclerView.Adapter<BookSearchAdapter.ItemHolder>(){

    private var searchData = ArrayList<SearchData.Documents>()

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookSearchAdapter.ItemHolder {
        return ItemHolder(LayoutInflater.from(parent.context).inflate(R.layout.search_item_view, parent, false))
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        onItemClickListener.let {
            holder.search_item_layout.setOnClickListener {
              onItemClickListener?.onClick(it, position, searchData[position])
            }
        }
        holder.bind(searchData[position])
    }

    fun notifyItem(changedData : ArrayList<SearchData.Documents>){
        searchData = changedData
        notifyDataSetChanged()
    }

    inner class ItemHolder(itemview : View) : RecyclerView.ViewHolder(itemview){
        val search_item_layout : LinearLayout= itemview.findViewById(R.id.search_item_layout)
        val book_name : TextView= itemview.findViewById(R.id.book_name)
        val book_contents : TextView = itemview.findViewById(R.id.book_contents)
        val book_image : ImageView = itemview.findViewById(R.id.book_image)

        fun bind(item: SearchData.Documents) {
            book_image.run {
                Glide.with(context).load(item.thumbnail).override(Target.SIZE_ORIGINAL).into(this)
            }
            book_name.text = item.title
            book_contents.text = item.authors?.get(0)?.toString() ?: ""
        }
    }

    var onItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onClick(
            view: View,
            position: Int,
            vo: SearchData.Documents
        )
    }

    fun onItemClick(listener: (view: View, position: Int, vo: SearchData.Documents) -> Unit) {
        onItemClickListener = object : OnItemClickListener {
            override fun onClick(view: View, position: Int, vo: SearchData.Documents) {
                listener(view, position, vo)
            }
        }
    }

}
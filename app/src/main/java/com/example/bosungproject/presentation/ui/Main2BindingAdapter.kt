package com.example.bosungproject.presentation.ui

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bosungproject.data.entity.SearchHistory
import com.example.bosungproject.domain.model.SearchData
import com.example.bosungproject.presentation.adapter.BookSearchAdapter
import com.example.bosungproject.presentation.adapter.SearchHistoryAdapter
import com.example.bosungproject.presentation.ui.viewModel.SearchViewModel

@BindingAdapter("bind_image")
fun bindImage(view: ImageView, url: String?) {
    Glide.with(view.context)
        .load(url)
        .into(view)
}

@BindingAdapter("item")
fun setBookList(
    recyclerView: RecyclerView,
    item: ArrayList<SearchHistory>?
) {
    val searchHistoryAdapter :SearchHistoryAdapter
    if(recyclerView.adapter == null){
        return
    }else{
        searchHistoryAdapter = recyclerView.adapter as SearchHistoryAdapter
    }
    item?.let {
        if(searchHistoryAdapter.historyList == it) return
        searchHistoryAdapter.historyList =  it.apply { it.reverse() }
        searchHistoryAdapter.notifyDataSetChanged()
    }
}


@BindingAdapter("item", "query")
fun setBookList(
    recyclerView: RecyclerView,
    item: SearchData?,
    query : String
) {
    val bookAdapter: BookSearchAdapter

    if (recyclerView.adapter == null) {
        return
    } else {
        bookAdapter = recyclerView.adapter as BookSearchAdapter
    }

    item?.let {
        if (item.documents.size == item.meta.total_count) {
            recyclerView.scrollToPosition(0)
            bookAdapter.page = -1
        } else{
            bookAdapter.page = (item.documents.size / 10) + 1
            if(bookAdapter.page == 1) recyclerView.scrollToPosition(0)
        }
        if(item.meta.is_end == true) bookAdapter.page = -1
        bookAdapter.query = query
        bookAdapter.searchList = it.documents
        bookAdapter.moreLoad = true
        bookAdapter.notifyDataSetChanged()
    }
}

@BindingAdapter("viewModel", "link")
fun openWep(
    textView: TextView,
    viewModel: SearchViewModel,
    link : String?
) {
    textView.setOnClickListener {
        if(link != null){
            viewModel.openWep(link)
        }
    }
}

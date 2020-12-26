package com.example.bosungproject.presentation.ui

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bosungproject.domain.model.SearchData
import com.example.bosungproject.presentation.adapter.BookSearchAdapter
import com.example.bosungproject.presentation.ui.viewModel.SearchViewModel

@BindingAdapter("item")
fun setBookList(
    recyclerView: RecyclerView,
    item: SearchData?
) {
    val bookAdapter: BookSearchAdapter

    if (recyclerView.adapter == null) {
        return
    } else {
        bookAdapter = recyclerView.adapter as BookSearchAdapter
    }

    //여기서 쿼리문을 비교해서 2가지 경우 처리
    recyclerView.scrollToPosition(0)

    item?.let {
        bookAdapter.moreLoad = it.meta.is_end
        bookAdapter.pageable_count = it.meta.pageable_count
        bookAdapter.searchData = it.documents
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

package com.example.bosungproject.presentation.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.bosungproject.R
import com.example.bosungproject.domain.model.KakaoSearchSortEnum
import com.example.bosungproject.domain.model.SearchQuery
import com.example.bosungproject.presentation.adapter.BookSearchAdapter
import com.example.bosungproject.presentation.adapter.LinearLayoutManagerWrapper
import com.example.bosungproject.presentation.ui.fragment.SearchFragment
import com.example.bosungproject.presentation.ui.viewModel.SearchViewModel
import com.example.bosungproject.presentation.util.BackPressCloseHandler
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.android.synthetic.main.search_detail.*
import kotlinx.android.synthetic.main.search_detail.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity2 : AppCompatActivity() {
    private val TAG = "MainActivity2"
    private lateinit var backPressCloseHandler: BackPressCloseHandler
    private lateinit var searchDetailBehavior: BottomSheetBehavior<View>
    private val viewModel: SearchViewModel by viewModel()

    private val bookSearchAdapter: BookSearchAdapter by lazy {
        BookSearchAdapter(viewModel)
    }

    private lateinit var fragment: SearchFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        backPressCloseHandler = BackPressCloseHandler(this)
        searchDetailBehavior = BottomSheetBehavior.from(search_detail_bottomSheet)
        fragment = SearchFragment.newInstance()
        searchCustomBar01.getSearchTextView().setOnClickListener {
            supportFragmentManager.beginTransaction().add(R.id.frame_layout, fragment, "search").addToBackStack(null).commit()
        }

        val linearLayoutManager = LinearLayoutManagerWrapper(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = bookSearchAdapter
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.setHasFixedSize(true)

        bookSearchAdapter.onItemClick { view, position, vo ->
            if (supportFragmentManager.backStackEntryCount != 0) {
                supportFragmentManager.popBackStackImmediate()
            }
            searchDetailBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            Glide.with(this@MainActivity2).load(vo.thumbnail).into(detail_image)
            detail_title.text = vo.title
            var authors = ""
            for (author in vo.authors!!){
                authors += "$author "
            }
            detail_authors.text = authors
            detail_publisher.text = vo.publisher
            detail_contents.text = vo.contents
            detail_image.setOnClickListener {
                val i = Intent(Intent.ACTION_VIEW, Uri.parse(vo.url))
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                i.setPackage("com.android.chrome")
                startActivity(i)
            }
        }
        initViewModel()

    }

    private fun initViewModel(){
        viewModel.search(SearchQuery("베스트 셀러", KakaoSearchSortEnum.Accuracy,null,null))
        viewModel.searchData.observe(this, Observer {
            bookSearchAdapter.notifyItem(it.documents)
        })
        viewModel.searchQuery.observe(this, Observer {
            searchCustomBar01.getSearchTextView().text = it
        })
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount != 0) {
            supportFragmentManager.popBackStackImmediate()
        } else {
            backPressCloseHandler.onBackPressed()
        }
    }

}
package com.example.bosungproject.presentation.ui

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.bosungproject.R
import com.example.bosungproject.databinding.ActivityMain2Binding
import com.example.bosungproject.databinding.SearchDetailBinding
import com.example.bosungproject.domain.model.KakaoSearchSortEnum
import com.example.bosungproject.domain.model.SearchQuery
import com.example.bosungproject.presentation.adapter.BookSearchAdapter
import com.example.bosungproject.presentation.adapter.LinearLayoutManagerWrapper
import com.example.bosungproject.presentation.ui.fragment.SearchFragment
import com.example.bosungproject.presentation.ui.viewModel.SearchViewModel
import com.example.bosungproject.presentation.util.BackPressCloseHandler
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.android.synthetic.main.search_detail.*
import kotlinx.android.synthetic.main.search_detail.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity2 : AppCompatActivity() {
    private val TAG = "MainActivity2"
    private lateinit var backPressCloseHandler: BackPressCloseHandler
    private lateinit var searchDetailBehavior: BottomSheetBehavior<View>
    private val viewModel: SearchViewModel by viewModel()

    lateinit var binding: ActivityMain2Binding

    private val bookSearchAdapter: BookSearchAdapter by lazy {
        BookSearchAdapter(viewModel)
    }

    private lateinit var fragment: SearchFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding  = DataBindingUtil.setContentView<ActivityMain2Binding>(this, R.layout.activity_main2)
            .apply{
                viewModel =this@MainActivity2.viewModel
                lifecycleOwner = this@MainActivity2     //BindingAdapter를 사용하기 위해서 필요함
                searchDetailBottomSheet.viewModel = this@MainActivity2.viewModel
                searchDetailBottomSheet.lifecycleOwner =  this@MainActivity2
            }

        binding.recyclerView.apply {
            adapter = bookSearchAdapter
            setHasFixedSize(true)
        }

        searchDetailBehavior = BottomSheetBehavior.from(search_detail_bottomSheet)
        bookSearchAdapter.onItemClick { view, position, data ->
            if (supportFragmentManager.backStackEntryCount != 0) {
                supportFragmentManager.popBackStackImmediate()
            }
            searchDetailBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            binding.searchDetailBottomSheet.item = data
            Glide.with(this@MainActivity2).load(data.thumbnail).into(binding.searchDetailBottomSheet.detailImage)
        }

        initViewModel()

        backPressCloseHandler = BackPressCloseHandler(this)

        fragment = SearchFragment.newInstance()
        searchCustomBar01.getSearchTextView().setOnClickListener {
            supportFragmentManager.beginTransaction().add(R.id.frame_layout, fragment, "search").addToBackStack(null).commit()
        }
    }

    private fun initViewModel(){
        viewModel.search(SearchQuery("베스트 셀러", KakaoSearchSortEnum.Accuracy,1,10))
        viewModel.searchQuery.observe(this, Observer {
            binding.searchCustomBar01.getSearchTextView().text = it
        })
        viewModel.openWep.observe(this, Observer {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.setPackage("com.android.chrome")
            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(this, "링크를 열수 없습니다.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount != 0) {
            supportFragmentManager.popBackStackImmediate()
        } else {
            if(searchDetailBehavior.state == BottomSheetBehavior.STATE_EXPANDED){
                searchDetailBehavior.state = BottomSheetBehavior.STATE_HIDDEN
                return
            }
            backPressCloseHandler.onBackPressed()
        }
    }
}
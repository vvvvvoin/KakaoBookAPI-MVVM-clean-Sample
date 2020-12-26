package com.example.bosungproject.presentation.ui.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.bosungproject.domain.model.KakaoSearchSortEnum
import com.example.bosungproject.domain.model.Result
import com.example.bosungproject.domain.model.SearchData
import com.example.bosungproject.domain.model.SearchQuery
import com.example.bosungproject.domain.usecase.SearchUseCase
import com.example.bosungproject.presentation.ui.viewModel.base.BaseViewModel

class SearchViewModel(
        private val searchUseCase: SearchUseCase
    ) : BaseViewModel() {
    private val TAG = "LogViewModel"
    private val searchResult = searchUseCase.observe()

    private var query = SearchQuery("", KakaoSearchSortEnum.Accuracy, null, null)

    private val _searchData = MediatorLiveData<SearchData>()
    val searchData: LiveData<SearchData>
        get() = _searchData

    private val _searchQuery = MediatorLiveData<String>()
    val searchQuery: LiveData<String>
        get() = _searchQuery

    private val _openWep = MediatorLiveData<String>()
    val openWep : LiveData<String>
        get() = _openWep

    init {
        _searchData.addSource(searchResult){
            when(it){
                is Result.Success -> {
                    _searchData.value = it.data!!
                    Log.d(TAG, "${it.data.meta.toString()}")
                }
                is Result.Failure -> {
                    Log.d(TAG, "실패 이유 : ${it.exception}")
                }
            }
        }
    }

    fun search(searchQuery : SearchQuery){
        if (searchQuery.query == query.query) {
            query.page?.plus(1)
            searchUseCase(searchQuery)
        } else {
            _searchQuery.value = searchQuery.query
            searchUseCase(searchQuery)
        }
    }

    fun openWep(link : String){
        _openWep.value = link
    }
}
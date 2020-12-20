package com.example.bosungproject.presentation.ui.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
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

    private val _searchData = MediatorLiveData<SearchData>()
    val searchData: LiveData<SearchData>
        get() = _searchData

    private val _searchQuery = MediatorLiveData<String>()
    val searchQuery: LiveData<String>
        get() = _searchQuery

    init {
        _searchData.addSource(searchResult){
            when(it){
                is Result.Success -> {
                    _searchData.value = it.data!!
                }
                is Result.Failure -> {
                    Log.d(TAG, "실패 이유 : ${it.exception}")
                }
            }
        }

    }

    fun search(searchQuery : SearchQuery){
        _searchQuery.value = searchQuery.query
        searchUseCase(searchQuery)
    }

}
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
import com.example.bosungproject.presentation.util.Event

class SearchViewModel(
        private val searchUseCase: SearchUseCase
    ) : BaseViewModel() {

    private val TAG = "LogViewModel"
    private val searchResult = searchUseCase.observe()

    var query = ""
    private var allBook = ArrayList<SearchData.Documents>()

    private val _searchData = MediatorLiveData<SearchData>()
    val searchData: LiveData<SearchData>
        get() = _searchData

    private val _searchQuery = MediatorLiveData<Event<String>>()
    val searchQuery: LiveData<Event<String>>
        get() = _searchQuery

    private val _openWep = MediatorLiveData<Event<String>>()
    val openWep : LiveData<Event<String>>
        get() = _openWep

    init {
        _searchData.addSource(searchResult){
            when(it){
                is Result.Success -> {
                    _searchData.value = it.data.apply {
                        allBook.addAll(this.documents)
                        this.documents.clear()
                        this.documents.addAll(allBook)
                    }
                }
                is Result.Failure -> {
                    if(it.exception == "network"){
                        _error.value = Event("network")
                    }else if(it.exception == "server"){
                        _error.value = Event("server")
                    }
                    Log.d(TAG, "실패 이유 : ${it.exception}")
                }
            }
        }
    }

    fun search(searchQuery : SearchQuery){
        if (searchQuery.page == 1){
            allBook.clear()
            _searchQuery.value = Event(searchQuery.query)
        }

        if (searchQuery.query == query) {
            searchUseCase(searchQuery)
        } else {
            query = searchQuery.query
            searchUseCase(searchQuery)
        }
    }

    fun openWep(link : String){
        _openWep.value = Event(link)
    }
}
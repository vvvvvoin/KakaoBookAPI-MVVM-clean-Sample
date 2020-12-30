package com.example.bosungproject.presentation.ui.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.bosungproject.data.entity.SearchHistory
import com.example.bosungproject.domain.model.KakaoSearchSortEnum
import com.example.bosungproject.domain.model.Result
import com.example.bosungproject.domain.model.SearchData
import com.example.bosungproject.domain.model.SearchQuery
import com.example.bosungproject.domain.repository.SearchHistoryRepository
import com.example.bosungproject.domain.usecase.GetHistoryUseCase
import com.example.bosungproject.domain.usecase.InsertHistoryUseCase
import com.example.bosungproject.domain.usecase.SearchUseCase
import com.example.bosungproject.presentation.ui.viewModel.base.BaseViewModel
import com.example.bosungproject.presentation.util.Event

class SearchViewModel(
        private val searchUseCase: SearchUseCase,
        private val  getHistoryUseCase: GetHistoryUseCase,
        private val insertHistoryUseCase: InsertHistoryUseCase
    ) : BaseViewModel() {

    private val TAG = "SearchViewModel"
    private val searchResult = searchUseCase.observe()
    private val getHistoryResult = getHistoryUseCase.observe()
    private val insertHistoryResult = insertHistoryUseCase.observe()

    var query = ""
    private var allBook = ArrayList<SearchData.Documents>()

    private val _searchHistory = MediatorLiveData<ArrayList<SearchHistory>>()
    val searchHistory: LiveData<ArrayList<SearchHistory>>
        get() = _searchHistory

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
        getHistoryUseCase(Unit)
        _searchHistory.addSource(getHistoryResult){
            when(it){
                is Result.Success -> {
                    _searchHistory.value = ArrayList(it.data.map {
                        SearchHistory(it.query)
                    })
                }
                is Result.Failure -> {
                    Log.d(TAG, "DB 데이터 불러오기 실패")
                }
            }
        }
        _searchQuery.addSource(insertHistoryResult){
            when(it){
                is Result.Success -> {
                    getHistoryUseCase(Unit)
                }
            }
        }

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
            if(searchQuery.query != "베스트 셀러" && searchQuery.query.isNotBlank()) insertHistoryUseCase(searchQuery.query)
            query = searchQuery.query
            searchUseCase(searchQuery)
        }
    }

    fun search(query: String){
        allBook.clear()
        _searchQuery.value = Event(query)
        this.query = query
        searchUseCase(SearchQuery(query, KakaoSearchSortEnum.Accuracy, 1, 10))
    }

    fun openWep(link : String){
        _openWep.value = Event(link)
    }
}
package com.example.bosungproject.data.dataSource

import com.example.bosungproject.data.db.SearchHistoryDatabase
import com.example.bosungproject.data.entity.SearchHistory
import io.reactivex.Single

class SearchHistoryLocalDataSource (private val searchHistoryDatabase: SearchHistoryDatabase){
    private val searchHistoryDao = searchHistoryDatabase.searchHistoryDao()

    fun insertSearchHistory(searchHistory : SearchHistory):Single<Long>{
        return searchHistoryDao.insert(searchHistory)
    }

    fun getAllSearchHistory():Single<List<SearchHistory>>{
        return searchHistoryDao.getAllSearchHistory()
    }
}
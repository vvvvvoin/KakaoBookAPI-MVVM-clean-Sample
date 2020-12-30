package com.example.bosungproject.data.repository

import com.example.bosungproject.data.dataSource.SearchHistoryLocalDataSource
import com.example.bosungproject.data.entity.SearchHistory
import com.example.bosungproject.data.mapper.SearchHistoryMapper
import com.example.bosungproject.domain.model.Result
import com.example.bosungproject.domain.repository.SearchHistoryRepository
import com.example.bosungproject.domain.repository.SearchRepository
import io.reactivex.Single

class SearchHistoryRepositoryImpl(private val searchHistoryLocalDataSource: SearchHistoryLocalDataSource) : SearchHistoryRepository{
    override fun insertSearchHistory(searchHistory: String): Single<Result<Long>> {
        return searchHistoryLocalDataSource.insertSearchHistory(SearchHistory(searchHistory)).map(SearchHistoryMapper::mapInsertToResult)
    }

    override fun getAllSearchHistory(): Single<Result<List<SearchHistory>>> {
        return searchHistoryLocalDataSource.getAllSearchHistory().map(SearchHistoryMapper::mapSearchWordToResult)
    }
}
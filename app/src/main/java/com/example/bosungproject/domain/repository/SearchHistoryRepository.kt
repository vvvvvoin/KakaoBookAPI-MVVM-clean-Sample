package com.example.bosungproject.domain.repository

import com.example.bosungproject.data.entity.SearchHistory
import com.example.bosungproject.domain.model.Result
import com.example.bosungproject.domain.model.SearchData
import io.reactivex.Single

interface SearchHistoryRepository {
    fun insertSearchHistory(query: String) : Single<Result<Long>>
    fun getAllSearchHistory(): Single<Result<List<SearchHistory>>>
}
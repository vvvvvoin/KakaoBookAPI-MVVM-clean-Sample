package com.example.bosungproject.data.repository

import com.example.bosungproject.data.dataSource.SearchRemoteDataSource
import com.example.bosungproject.data.mapper.SearchDataMapper
import com.example.bosungproject.domain.model.KakaoSearchSortEnum
import io.reactivex.Single
import com.example.bosungproject.domain.model.Result
import com.example.bosungproject.domain.model.SearchData
import com.example.bosungproject.domain.model.SearchQuery
import com.example.bosungproject.domain.repository.SearchRepository

class SearchRepositoryImpl(private val remoteDataDataSource : SearchRemoteDataSource) : SearchRepository {
    override fun search(searchQuery: SearchQuery): Single<Result<SearchData>> {
        return remoteDataDataSource.search(searchQuery).map(SearchDataMapper::map)
    }
}
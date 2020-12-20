package com.example.bosungproject.data.dataSource

import com.example.bosungproject.data.api.SearchAPIService
import com.example.bosungproject.domain.model.KakaoSearchSortEnum
import com.example.bosungproject.domain.model.SearchData
import com.example.bosungproject.domain.model.SearchQuery
import io.reactivex.Single
import retrofit2.Response

class SearchRemoteDataSource(private val api : SearchAPIService) {
    private val API_KEY = "KakaoAK your_kakao_rest_api"

    fun search(searchQuery:SearchQuery): Single<Response<SearchData>> {
        return api.search(auth = API_KEY, query = searchQuery.query, sort = searchQuery.sort?.sort, page = searchQuery.page, size = searchQuery.size)
    }
}
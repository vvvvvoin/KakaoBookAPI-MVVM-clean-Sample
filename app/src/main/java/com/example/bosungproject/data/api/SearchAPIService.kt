package com.example.bosungproject.data.api

import com.example.bosungproject.domain.model.SearchData
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.*

interface SearchAPIService {
    @GET("/v3/search/book")
    fun search(
        @Header("Authorization") auth : String,
        @Query("query") query: String,
        @Query("sort") sort: String?,
        @Query("page") page: Int?,
        @Query("size") size: Int?
    ) : Single<Response<SearchData>>
}
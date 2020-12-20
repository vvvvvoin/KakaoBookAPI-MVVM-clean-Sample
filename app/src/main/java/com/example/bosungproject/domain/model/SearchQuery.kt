package com.example.bosungproject.domain.model

import retrofit2.http.Query

data class SearchQuery (
    var query: String,
    var sort: KakaoSearchSortEnum?,
    var page: Int?,
    var size: Int?
)
package com.example.bosungproject.domain.model


data class SearchQuery (
    var query: String,
    var sort: KakaoSearchSortEnum,
    var page: Int?,
    var size: Int?
)
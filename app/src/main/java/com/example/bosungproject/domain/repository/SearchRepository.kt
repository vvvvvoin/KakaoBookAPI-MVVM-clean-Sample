package com.example.bosungproject.domain.repository

import com.example.bosungproject.domain.model.*
import io.reactivex.Single

interface SearchRepository {
    fun search(searchQuery: SearchQuery) : Single<Result<SearchData>>
}
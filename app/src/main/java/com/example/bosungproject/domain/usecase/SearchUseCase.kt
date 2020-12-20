package com.example.bosungproject.domain.usecase

import com.example.bosungproject.domain.model.Result
import com.example.bosungproject.domain.model.SearchData
import com.example.bosungproject.domain.model.SearchQuery
import com.example.bosungproject.domain.repository.SearchRepository
import io.reactivex.Single

class SearchUseCase(private val repository : SearchRepository) : SingleUseCase<SearchQuery, SearchData>()  {
    override fun execute(parameter: SearchQuery): Single<Result<SearchData>> {
        return repository.search(parameter)
    }
}
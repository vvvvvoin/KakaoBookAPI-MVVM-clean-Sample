package com.example.bosungproject.domain.usecase

import com.example.bosungproject.data.entity.SearchHistory
import com.example.bosungproject.domain.model.Result
import com.example.bosungproject.domain.repository.SearchHistoryRepository
import io.reactivex.Single

class GetHistoryUseCase(private val searchHistoryRepository: SearchHistoryRepository): SingleUseCase<Unit, List<SearchHistory>>() {
    override fun execute(parameter: Unit): Single<Result<List<SearchHistory>>> {
        return searchHistoryRepository.getAllSearchHistory()
    }
}
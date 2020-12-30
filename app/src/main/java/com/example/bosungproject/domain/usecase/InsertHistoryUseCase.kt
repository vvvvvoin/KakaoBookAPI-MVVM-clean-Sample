package com.example.bosungproject.domain.usecase

import com.example.bosungproject.data.entity.SearchHistory
import com.example.bosungproject.domain.model.Result
import com.example.bosungproject.domain.repository.SearchHistoryRepository
import io.reactivex.Single

class InsertHistoryUseCase (private val searchHistoryRepository: SearchHistoryRepository): SingleUseCase<String, Long>() {
    override fun execute(parameter: String): Single<Result<Long>> {
        return searchHistoryRepository.insertSearchHistory(parameter)
    }
}
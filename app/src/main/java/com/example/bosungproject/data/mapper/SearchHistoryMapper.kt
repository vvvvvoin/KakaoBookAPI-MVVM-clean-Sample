package com.example.bosungproject.data.mapper

import com.example.bosungproject.data.entity.SearchHistory
import com.example.bosungproject.domain.model.Result

object SearchHistoryMapper {
    fun mapSearchWordToResult(searchWordList : List<SearchHistory>) : Result<List<SearchHistory>> {
        return if (searchWordList.isEmpty())
            Result.Failure("server")
        else
            Result.Success(searchWordList)
    }

    fun  mapInsertToResult(value : Long) : Result<Long> {
        return if (value.toInt() != -1)
            Result.Success(value)
        else
            Result.Failure("server")
    }
}
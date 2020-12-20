package com.example.bosungproject.data.mapper

import com.example.bosungproject.domain.model.Result
import com.example.bosungproject.domain.model.SearchData

object SearchDataMapper : NetworkMapper<SearchData>() {
    override fun mapTo(data: SearchData): Result<SearchData> {
        return if (data != null) {
            Result.Success(data)
        } else
            Result.Failure("server")
    }
}
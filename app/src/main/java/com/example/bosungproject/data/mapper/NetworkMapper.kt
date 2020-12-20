package com.example.bosungproject.data.mapper
import  com.example.bosungproject.domain.model.Result

abstract class NetworkMapper<R> {
    fun map(data: retrofit2.Response<R>): Result<R> {
        return if (data.isSuccessful) {
            data.body()?.let {
                mapTo(it)
            } ?: run {
                Result.Failure("server")
            }
        } else {
            Result.Failure("network")
        }
    }

    abstract fun mapTo(data: R): Result<R>
}
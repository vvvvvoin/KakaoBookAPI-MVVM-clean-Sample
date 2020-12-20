package com.example.bosungproject.domain.usecase

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import com.example.bosungproject.domain.model.Result

abstract class SingleUseCase<T, R>() {
    private val result = MutableLiveData<Result<R>>()
    fun observe() = result

    @SuppressLint("CheckResult")
    operator fun invoke(parameter: T): Disposable {
        return execute(parameter).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                result.value = it
            }, {
                result.value = Result.Failure(it.message!!)
            })
    }

    abstract fun execute(parameter: T): Single<Result<R>>
}

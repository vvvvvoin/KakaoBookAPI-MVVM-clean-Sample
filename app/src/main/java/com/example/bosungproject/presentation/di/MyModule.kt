package com.example.bosungproject.presentation.di


import com.example.bosungproject.data.api.SearchAPIService
import com.example.bosungproject.data.dataSource.SearchRemoteDataSource
import com.example.bosungproject.data.repository.SearchRepositoryImpl
import com.example.bosungproject.domain.repository.SearchRepository
import com.example.bosungproject.domain.usecase.SearchUseCase
import com.example.bosungproject.presentation.ui.viewModel.SearchViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val CONNECT_TIMEOUT: Long = 15
private const val WRITE_TIMEOUT: Long = 15
private const val READ_TIMEOUT: Long = 15

val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}

val client = OkHttpClient.Builder().apply {
    addInterceptor(httpLoggingInterceptor)
    connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
    writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
    readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
}.build()

val retrofit: Retrofit = Retrofit
    .Builder()
    .baseUrl("https://dapi.kakao.com")
    .client(client)
    .addConverterFactory(GsonConverterFactory.create())
    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    .build()

private val bookSearchApi: SearchAPIService = retrofit.create(SearchAPIService::class.java)

var retrofitPart = module {
    single{bookSearchApi }
}

val dataSourceModule = module {
    factory { SearchRemoteDataSource(get()) }
}

val repositoryModule = module {
    factory<SearchRepository> { SearchRepositoryImpl(get()) }
}

val useCaseModule = module {
    factory { SearchUseCase(get()) }
}

val viewModelModule = module {
    viewModel { SearchViewModel(get())}
}

var myDiModule = listOf(viewModelModule, useCaseModule, repositoryModule, dataSourceModule, retrofitPart)
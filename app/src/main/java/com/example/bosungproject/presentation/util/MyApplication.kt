package com.example.bosungproject.presentation.util

import android.app.Application
import com.example.bosungproject.presentation.di.myDiModule
import org.koin.android.ext.android.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(applicationContext, myDiModule)
    }
}
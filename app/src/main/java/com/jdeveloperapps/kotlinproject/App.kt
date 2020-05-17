package com.jdeveloperapps.kotlinproject

import android.app.Application
import com.jdeveloperapps.kotlinproject.di.appModule
import com.jdeveloperapps.kotlinproject.di.mainModule
import com.jdeveloperapps.kotlinproject.di.noteModule
import com.jdeveloperapps.kotlinproject.di.splashModule
import org.koin.android.ext.android.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(appModule, splashModule, mainModule, noteModule))
    }

}
package com.torres.pruebatecnica.aplication

import android.app.Application
import com.torres.pruebatecnica.di.*
import com.torres.pruebatecnica.exception.CrashActivity
import com.torres.pruebatecnica.exception.GlobalExceptionHandler
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App:Application() {


    override fun onCreate() {
        super.onCreate()

        appInstance = this
        GlobalExceptionHandler.initialize(this, CrashActivity::class.java)
        startKoin {
            androidContext(androidContext = this@App)
            modules(datasourceModule)
            modules(repositoryModule)
            modules(homeViewModel)
            modules(detailViewModel)
        }
    }

    companion object {
        var appInstance: App? = null
    }
}
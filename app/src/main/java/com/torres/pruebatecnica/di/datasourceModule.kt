package com.torres.pruebatecnica.di

import com.torres.pruebatecnica.aplication.App
import com.torres.pruebatecnica.data.DataSource
import com.torres.pruebatecnica.data.DataSourceImp
import com.torres.pruebatecnica.vo.AppDatabase
import org.koin.dsl.module

val datasourceModule = module {
    single<DataSource> { DataSourceImp(appDatabase = AppDatabase.getDatabase(App.appInstance!!.applicationContext)) }
}
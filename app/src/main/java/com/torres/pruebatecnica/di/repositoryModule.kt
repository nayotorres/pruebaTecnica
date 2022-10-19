package com.torres.pruebatecnica.di

import com.torres.pruebatecnica.domain.DataRepository
import com.torres.pruebatecnica.domain.DataRepositoryImp
import org.koin.dsl.module

val repositoryModule = module {
    single<DataRepository> { DataRepositoryImp(remoteDataSource = get()) }
}
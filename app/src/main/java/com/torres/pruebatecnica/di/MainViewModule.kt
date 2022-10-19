package com.torres.pruebatecnica.di

import com.torres.pruebatecnica.viewmodel.DetailViewModel
import com.torres.pruebatecnica.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeViewModel = module{
    viewModel { HomeViewModel(repository = get()) }
}

val detailViewModel = module {
    viewModel { DetailViewModel(repository = get()) }
}


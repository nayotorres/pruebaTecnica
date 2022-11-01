package com.torres.pruebatecnica

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.torres.pruebatecnica.di.datasourceModule
import com.torres.pruebatecnica.di.detailViewModel
import com.torres.pruebatecnica.di.homeViewModel
import com.torres.pruebatecnica.di.repositoryModule
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.koin.dsl.koinApplication
//import org.koin.test.check.checkModules

@RunWith(AndroidJUnit4::class)
class koinTest   {

    @Test
     fun setup(){
        koinApplication {
            modules(datasourceModule, repositoryModule, homeViewModel, detailViewModel)
           // checkModules()
        }
    }

    @After
    fun afterAndroid() {
        stopKoin()
    }


}
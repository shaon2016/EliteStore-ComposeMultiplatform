package com.ashiq.elitestore.di

import org.koin.core.context.startKoin
import org.koin.core.module.Module

// For Android
fun initKoin(additionalModule: List<Module>) = startKoin {
    modules(additionalModule)
    modules(
        networkModule,
        repositoryModule,
        viewModelModule,
        platformModule
    )
}

expect val platformModule: Module

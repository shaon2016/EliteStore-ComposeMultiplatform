package com.ashiq.elitestore.di

import androidx.compose.runtime.Composable
import org.koin.compose.KoinApplication
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration

// For Android
fun initKoin(additionalModule : List<Module>) = startKoin {
    modules(additionalModule)
    modules(
        networkModule,
        repositoryModule,
        viewModelModule,
        platformModule
    )
}

// For Compose
@Composable
fun MyKoinApplication(content: @Composable () -> Unit) {
    KoinApplication(
        application = {
            modules(
                networkModule,
                repositoryModule,
                viewModelModule,
                platformModule
            )
        }
    ) {
        content()
    }
}

expect val platformModule: Module

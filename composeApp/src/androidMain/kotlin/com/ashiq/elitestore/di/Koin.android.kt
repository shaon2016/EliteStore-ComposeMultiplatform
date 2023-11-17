package com.ashiq.elitestore.di

import com.ashiq.elitestore.data.local.DatabaseDriverFactory
import org.koin.dsl.module

actual val platformModule = module {
    single { DatabaseDriverFactory(context = get()) }
}
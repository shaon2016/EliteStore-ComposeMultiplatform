package com.ashiq.elitestore

import android.app.Application
import android.content.Context
import com.ashiq.elitestore.di.initKoin
import org.koin.dsl.module

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin(
            additionalModule = listOf(
                module {
                    single<Context> { this@MyApp }
                }
            )
        )
    }
}
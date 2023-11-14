package com.ashiq.elitestore

import android.app.Application
import di.initKoin

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin()
    }
}
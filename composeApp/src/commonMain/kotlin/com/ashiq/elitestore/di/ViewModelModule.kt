package com.ashiq.elitestore.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import com.ashiq.elitestore.ui.home.HomeViewModel
import com.ashiq.elitestore.ui.SharedViewModel

fun viewModelModule() = module {
    singleOf(::HomeViewModel)
    singleOf(::SharedViewModel)
}
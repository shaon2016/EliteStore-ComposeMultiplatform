package com.ashiq.elitestore.di

import com.ashiq.elitestore.ui.SharedViewModel
import com.ashiq.elitestore.ui.cart.CartViewModel
import com.ashiq.elitestore.ui.details.DetailsViewModel
import com.ashiq.elitestore.ui.home.HomeViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val viewModelModule = module {
    singleOf(::HomeViewModel) // Singleton
    singleOf(::SharedViewModel)
    factory { DetailsViewModel(get()) } // new Instance every time
    factory { CartViewModel(get()) }
}
package di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ui.home.HomeViewModel
import ui.SharedVM

fun viewModelModule() = module {
    singleOf(::HomeViewModel)
    singleOf(::SharedVM)
}
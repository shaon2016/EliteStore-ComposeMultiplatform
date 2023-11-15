package di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ui.home.HomeViewModel

fun viewModelModule() = module {
    singleOf(::HomeViewModel)
}
package com.ashiq.elitestore.di

import com.ashiq.elitestore.data.repository.ProductRepositoryImpl
import com.ashiq.elitestore.domain.repository.ProductRepository
import org.koin.dsl.module

fun repositoryModule() = module {
    single<ProductRepository> { ProductRepositoryImpl(client = get()) }
}
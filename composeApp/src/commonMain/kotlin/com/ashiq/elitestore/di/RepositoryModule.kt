package com.ashiq.elitestore.di

import com.ashiq.elitestore.data.repository.CartRepositoryImpl
import com.ashiq.elitestore.data.repository.ProductRepositoryImpl
import com.ashiq.elitestore.domain.repository.CartRepository
import com.ashiq.elitestore.domain.repository.ProductRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory<ProductRepository> { ProductRepositoryImpl(client = get()) }
    factory<CartRepository> { CartRepositoryImpl(databaseDriverFactory = get()) }
}
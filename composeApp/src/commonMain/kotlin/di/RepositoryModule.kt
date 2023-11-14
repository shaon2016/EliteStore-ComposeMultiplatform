package di

import data.repository.ProductRepositoryImpl
import domain.repository.ProductRepository
import org.koin.dsl.module

fun repositoryModule() = module {
    single<ProductRepository> { ProductRepositoryImpl(client = get()) }
}
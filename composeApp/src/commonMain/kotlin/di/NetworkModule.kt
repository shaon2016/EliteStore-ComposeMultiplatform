package di

import io.ktor.client.HttpClient
import org.koin.dsl.module

fun networkModule() = module {
    single {
        HttpClient {

        }
    }
}
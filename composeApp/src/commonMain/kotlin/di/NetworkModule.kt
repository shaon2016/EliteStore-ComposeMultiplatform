package di

import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

fun networkModule() = module {
    single {
        HttpClient {
            defaultRequest {
                url {
                    protocol = URLProtocol.HTTPS
                    host = "fakestoreapi.com"
                }
            }

            install(Logging) {
                level = LogLevel.ALL
                logger = object : Logger {
                    override fun log(message: String) {
                        Napier.i(tag = "http_client", message = message)
                    }
                }
            }.also {
                Napier.base(DebugAntilog())
            }

            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        isLenient = true
                    }
                )
            }
        }
    }
}
package di

import androidx.compose.runtime.Composable
import org.koin.compose.KoinApplication
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

// For Android
fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {
    appDeclaration()
    modules(networkModule(), repositoryModule(), viewModelModule())
}

// For iOS
fun KoinApplication.Companion.start(): KoinApplication = initKoin { }

// For Compose
@Composable
fun MyKoinApplication(content: @Composable () -> Unit) {
    KoinApplication(
        application = {
            modules(
                networkModule(),
                repositoryModule(),
                viewModelModule()
            )
        }
    ) {
        content()
    }
}

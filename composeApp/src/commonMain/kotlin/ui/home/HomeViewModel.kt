package ui.home

import domain.repository.ProductRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch

class HomeViewModel(private val productRepository: ProductRepository) {
    private val scope = CoroutineScope(Dispatchers.IO)

    init {
        scope.launch {
            productRepository.getProducts()
        }
    }
}
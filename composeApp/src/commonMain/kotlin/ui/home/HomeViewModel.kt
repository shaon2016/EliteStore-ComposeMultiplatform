package ui.home

import data.network.util.NetworkResult
import domain.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ui.BaseViewModel
import ui.DataState

class HomeViewModel(private val productRepository: ProductRepository) :
    BaseViewModel<HomeContract.Event, HomeContract.Effect>() {

    private val _state = MutableStateFlow(HomeContract.State(isLoading = true))
    val state = _state.asStateFlow()

    override fun handleEvents(event: HomeContract.Event) {
        when (event) {
            is HomeContract.Event.Initialize -> {
                getProducts()
            }
        }
    }

    private fun getProducts() {
        viewModelScope.launch {
            when (val result = productRepository.getProducts()) {
                is NetworkResult.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            products = result.data,
                            dataState = DataState.SUCCESS
                        )
                    }
                }

                is NetworkResult.Failure -> _state.update {
                    it.copy(
                        isLoading = false,
                        dataState = DataState.FAILED
                    )
                }
            }
        }
    }
}
package ui.home

import data.network.util.NetworkResult
import domain.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.viewModelScope
import ui.component.BaseViewModel
import util.UIState

class HomeViewModel(private val productRepository: ProductRepository) :
    BaseViewModel<HomeContract.Event, HomeContract.Effect>() {

    private val _state = MutableStateFlow(HomeContract.State(isLoading = true))
    val state = _state.asStateFlow()

    override fun handleEvents(event: HomeContract.Event) {
        when (event) {
            is HomeContract.Event.Initialize -> {
                getProducts()
            }

            is HomeContract.Event.ToDetails -> setEffect {
                HomeContract.Effect.Navigation.ToDetails(event.product)
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
                            UIState = UIState.SUCCESS
                        )
                    }
                }

                is NetworkResult.Failure -> _state.update {
                    it.copy(
                        isLoading = false,
                        UIState = UIState.FAILED
                    )
                }
            }
        }
    }
}
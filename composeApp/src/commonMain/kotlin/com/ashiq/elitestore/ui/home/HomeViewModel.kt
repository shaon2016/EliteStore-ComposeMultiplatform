package com.ashiq.elitestore.ui.home

import com.ashiq.elitestore.util.Result
import com.ashiq.elitestore.domain.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.viewModelScope
import com.ashiq.elitestore.ui.component.BaseViewModel
import com.ashiq.elitestore.util.UIState

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
                is Result.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            products = result.data,
                            UIState = UIState.SUCCESS
                        )
                    }
                }

                is Result.Failure -> _state.update {
                    it.copy(
                        isLoading = false,
                        UIState = UIState.FAILED
                    )
                }
            }
        }
    }
}
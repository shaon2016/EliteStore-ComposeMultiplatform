package com.ashiq.elitestore.ui.component

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import org.koin.core.component.KoinComponent

const val LAUNCH_LISTEN_FOR_EFFECTS = "launch-listen-to-effects"

interface ViewEvent
interface ViewSideEffect

abstract class BaseViewModel<EVENT : ViewEvent, EFFECT : ViewSideEffect>
    : ViewModel(), KoinComponent {

    private var initializeCalled = false

    protected fun initialize(executable: suspend () -> Unit) {
        if (initializeCalled) return
        initializeCalled = true
        viewModelScope.launch {
            executable()
        }
    }

    // Event
    private val _event = MutableSharedFlow<EVENT>()

    abstract fun handleEvents(event: EVENT)

    fun setEvent(event: EVENT) {
        viewModelScope.launch { _event.emit(event) }
    }

    private fun collectEvents() {
        viewModelScope.launch {
            _event.collect {
                handleEvents(it)
            }
        }
    }

    init {
        collectEvents()
    }

    // Effect
    private val _effect = MutableSharedFlow<EFFECT>()
    val effect = _effect

    protected fun setEffect(effectHandler: () -> EFFECT) {
        val newEffect = effectHandler()
        viewModelScope.launch { _effect.emit(newEffect) }
    }
}
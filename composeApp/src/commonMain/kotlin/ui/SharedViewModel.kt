package ui

import domain.entity.Product
import moe.tlaster.precompose.viewmodel.ViewModel
import org.koin.core.component.KoinComponent

class SharedViewModel : ViewModel() {
    internal lateinit var product: Product
}
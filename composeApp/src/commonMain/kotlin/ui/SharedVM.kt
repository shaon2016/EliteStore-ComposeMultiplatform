package ui

import domain.entity.Product
import org.koin.core.component.KoinComponent

class SharedVM : KoinComponent {
    internal lateinit var product: Product
}
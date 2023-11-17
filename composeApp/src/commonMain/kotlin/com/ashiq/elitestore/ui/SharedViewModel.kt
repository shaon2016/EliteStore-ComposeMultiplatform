package com.ashiq.elitestore.ui

import com.ashiq.elitestore.domain.entity.Product
import moe.tlaster.precompose.viewmodel.ViewModel
import org.koin.core.component.KoinComponent

class SharedViewModel : ViewModel(), KoinComponent {
    internal lateinit var product: Product
}
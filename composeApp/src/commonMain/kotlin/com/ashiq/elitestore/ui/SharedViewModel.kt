package com.ashiq.elitestore.ui

import com.ashiq.elitestore.domain.entity.Product
import moe.tlaster.precompose.viewmodel.ViewModel

class SharedViewModel : ViewModel() {
    internal lateinit var product: Product
}
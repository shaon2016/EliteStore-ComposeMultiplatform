package com.ashiq.elitestore.util

import kotlin.math.roundToInt

fun Double.roundTo(): Double = this.times(100.0).roundToInt() / 100.0
fun Float.roundTo(): Double = this.times(100.0).roundToInt() / 100.0
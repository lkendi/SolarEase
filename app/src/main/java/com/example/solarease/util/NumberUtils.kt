package com.example.solarease.util

fun Double.format(decimalPlaces: Int) = "%.${decimalPlaces}f".format(this)

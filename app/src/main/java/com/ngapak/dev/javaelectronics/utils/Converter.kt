package com.ngapak.dev.javaelectronics.utils

import java.text.NumberFormat
import java.util.Locale

object Converter {
    fun Int.toRupiah(): String {
        val currency = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
        return currency.format(this)
    }
}
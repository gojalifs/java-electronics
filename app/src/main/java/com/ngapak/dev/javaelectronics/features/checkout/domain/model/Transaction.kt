package com.ngapak.dev.javaelectronics.features.checkout.domain.model

import com.ngapak.dev.javaelectronics.features.home.domain.model.Product

data class Transaction(
    val name: String? = null,
    val totalPrice: Int? = null,
    val qty: Int? = null,
    val deliveryStatus: String? = null,
    val address: Map<String, Any>? = null,
    val product: Product? = null
)
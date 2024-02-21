package com.ngapak.dev.javaelectronics.features.checkout.domain.model

import com.google.firebase.firestore.DocumentId
import com.ngapak.dev.javaelectronics.features.product.domain.model.ProductDetail

data class Transaction(
    @DocumentId
    val id: String? = null,
    val deliveryStatus: String? = null,
    val receiptNumber: String? = null,
    val paymentStatus: String? = null,
    val qty: Int? = null,
    val totalPrice: Int? = null,
    val address: Address? = null,
    val product: ProductDetail? = null,
)
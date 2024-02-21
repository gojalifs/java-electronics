package com.ngapak.dev.javaelectronics.features.product.domain.model

import com.google.firebase.firestore.DocumentId

data class ProductDetail(
    @DocumentId
    val id: String? = null,
    val name: String? = null,
    val stock: Int? = null,
    val description: String? = null,
    val category: String? = null,
    val imageUrl: String? = null,
    val price: Int? = null,
)

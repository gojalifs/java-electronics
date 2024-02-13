package com.ngapak.dev.javaelectronics.features.home.domain.model

import com.google.firebase.firestore.DocumentId

data class Product(
    @DocumentId
    val documentId: String? = null,
    val name: String? = null,
    val category: String? = null,
    val imageUrl: String? = null,
)

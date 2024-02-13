package com.ngapak.dev.javaelectronics.features.checkout.domain.model

import com.google.firebase.firestore.DocumentId

data class Address(
    @DocumentId
    val id: String? = null,
    val receiverName: String? = null,
    val phone: String? = null,
    val address: String? = null,
    val addressDetail: String? = null,
) {
    fun toMap(): Map<String, Any> {
        return mapOf(
            "id" to (id ?: ""),
            "receiverName" to (receiverName ?: ""),
            "phone" to (phone ?: ""),
            "address" to (address ?: ""),
            "addressDetail" to (addressDetail ?: ""),
        )
    }
}

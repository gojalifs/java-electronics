package com.ngapak.dev.javaelectronics.features.history.presentation

import androidx.lifecycle.ViewModel
import com.ngapak.dev.javaelectronics.features.checkout.domain.model.Address
import com.ngapak.dev.javaelectronics.features.checkout.domain.model.Transaction

class HistoryViewModel : ViewModel() {
    var waitingPayment = arrayListOf(
        Transaction(
            "Blackberry  Gemini",
            3000000,
            1,
            "waiting-payment",
            Address(
                "eruiewhudvd",
                "Fajar",
                "08427372842",
                "Jalan Cikarang Raya, Cikarang Utara",
                "Patokan masjid agung"
            ).toMap()
        ),
    )

    var packing = arrayListOf(
        Transaction(
            "Samsung A54 5G",
            5000000,
            1,
            "packing",
            Address(
                "eruiewhudvd",
                "Fajar",
                "08427372842",
                "Jalan Cikarang Raya, Cikarang Utara",
                "Patokan masjid agung"
            ).toMap()
        ),
        Transaction(
            "Samsung A54s 5G",
            5000000,
            1,
            "packing",
            Address(
                "eruiewhudvdsd",
                "Fajar",
                "08427372842",
                "Jalan Cikarang Raya, Cikarang Utara",
                "Patokan masjid agung"
            ).toMap()
        )
    )

    var delivering = arrayListOf(
        Transaction(
            "Samsung S24 5G",
            5000000,
            1,
            "delivering",
            Address(
                "eruiewhudvd",
                "Fajar",
                "08427372842",
                "Jalan Cikarang Raya, Cikarang Utara",
                "Patokan masjid agung"
            ).toMap()
        )
    )

    var completed = arrayListOf(
        Transaction(
            "Huawei P60 Pro",
            5000000,
            1,
            "completed",
            Address(
                "eruiewhudvd",
                "Fajar",
                "08427372842",
                "Jalan Cikarang Raya, Cikarang Utara",
                "Patokan masjid agung"
            ).toMap()
        )
    )

    var cancelled = arrayListOf(
        Transaction(
            "iPhone 15 Pro Max",
            35000000,
            1,
            "cancelled",
            Address(
                "eruiewhudvd",
                "Fajar",
                "08427372842",
                "Jalan Cikarang Raya, Cikarang Utara",
                "Patokan masjid agung"
            ).toMap()
        )
    )

    private var _orders = Transaction()
    val orders: Transaction get() = _orders

    fun setOrders(transaction: Transaction) {
        _orders = transaction
    }

    fun orderReceived(transaction: Transaction) {
        delivering.remove(transaction)
        completed.add(transaction)
    }

    fun addPaid(transaction: Transaction){
        packing.add(transaction)
    }
}

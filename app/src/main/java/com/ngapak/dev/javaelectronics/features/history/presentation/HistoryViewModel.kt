package com.ngapak.dev.javaelectronics.features.history.presentation

import androidx.lifecycle.ViewModel
import com.ngapak.dev.javaelectronics.features.checkout.domain.model.Transaction

class HistoryViewModel : ViewModel() {
    var waitingPayment = arrayListOf<Transaction>()

    var packing = arrayListOf<Transaction>()

    var delivering = arrayListOf<Transaction>()

    var completed = arrayListOf<Transaction>()

    var cancelled = arrayListOf<Transaction>()

    private var _orders = Transaction()
    val orders: Transaction get() = _orders

    fun setOrders(transaction: Transaction) {
        _orders = transaction
    }

    fun orderReceived(transaction: Transaction) {
        delivering.remove(transaction)
        completed.add(transaction)
    }

    fun addPaid(transaction: Transaction) {
        packing.add(transaction)
    }
}

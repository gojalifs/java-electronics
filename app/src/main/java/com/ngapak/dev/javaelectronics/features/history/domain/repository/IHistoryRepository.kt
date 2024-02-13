package com.ngapak.dev.javaelectronics.features.history.domain.repository

import com.ngapak.dev.javaelectronics.core.Resource
import com.ngapak.dev.javaelectronics.features.checkout.domain.model.Transaction
import kotlinx.coroutines.flow.Flow

interface IHistoryRepository {
    fun getWaitingPayment(): Flow<Resource<List<Transaction>>>
}
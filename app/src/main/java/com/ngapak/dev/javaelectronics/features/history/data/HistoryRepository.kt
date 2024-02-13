package com.ngapak.dev.javaelectronics.features.history.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.ngapak.dev.javaelectronics.core.Resource
import com.ngapak.dev.javaelectronics.features.checkout.domain.model.Transaction
import com.ngapak.dev.javaelectronics.features.history.domain.repository.IHistoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class HistoryRepository(private val auth: FirebaseAuth, private val db: FirebaseFirestore) :
    IHistoryRepository {
    override fun getWaitingPayment(): Flow<Resource<List<Transaction>>> = flow {
        emit(Resource.Loading())

        try {
            if (auth.currentUser != null){
                db.collection("users").document(auth.currentUser!!.uid).collection("transaction")
            }
        } catch (e:Exception){}
    }
}
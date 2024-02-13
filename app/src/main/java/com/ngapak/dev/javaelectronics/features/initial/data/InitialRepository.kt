package com.ngapak.dev.javaelectronics.features.initial.data

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ngapak.dev.javaelectronics.features.initial.domain.repository.IInitialRepository

class InitialRepository : IInitialRepository {
    override fun checkSession(): Boolean {
        val auth = Firebase.auth
        return auth.currentUser != null
    }
}
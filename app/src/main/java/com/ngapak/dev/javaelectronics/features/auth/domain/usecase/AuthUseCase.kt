package com.ngapak.dev.javaelectronics.features.auth.domain.usecase

import com.google.firebase.auth.FirebaseUser
import com.ngapak.dev.javaelectronics.core.Resource
import kotlinx.coroutines.flow.Flow

interface AuthUseCase {
    fun signInWithPassword(email: String, password: String): Flow<Resource<FirebaseUser?>>
    fun signUpWithPassword(email: String, password: String): Flow<Resource<Boolean>>
}
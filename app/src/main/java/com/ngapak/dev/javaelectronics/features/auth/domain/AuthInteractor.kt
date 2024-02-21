package com.ngapak.dev.javaelectronics.features.auth.domain

import com.google.firebase.auth.FirebaseUser
import com.ngapak.dev.javaelectronics.core.Resource
import com.ngapak.dev.javaelectronics.features.auth.domain.repository.IAuthRepository
import com.ngapak.dev.javaelectronics.features.auth.domain.usecase.AuthUseCase
import kotlinx.coroutines.flow.Flow

class AuthInteractor(private val authRepository: IAuthRepository) : AuthUseCase {
    override fun signInWithPassword(
        email: String,
        password: String
    ): Flow<Resource<FirebaseUser?>> {
        return authRepository.signInWithPassword(email, password)
    }

    override fun signUpWithPassword(email: String, password: String): Flow<Resource<Boolean>> {
        return authRepository.signUpWithPassword(email, password)
    }

    override fun signOut(): Flow<Resource<Boolean>> {
        return authRepository.signOut()
    }
}
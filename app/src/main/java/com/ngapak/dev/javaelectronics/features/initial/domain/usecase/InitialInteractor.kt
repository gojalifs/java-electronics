package com.ngapak.dev.javaelectronics.features.initial.domain.usecase

import com.ngapak.dev.javaelectronics.features.initial.data.InitialRepository
import com.ngapak.dev.javaelectronics.features.initial.domain.repository.IInitialRepository

class InitialInteractor(private val initialRepository: IInitialRepository) : InitialUseCase {
    override fun checkSession(): Boolean {
        return initialRepository.checkSession()
    }
}
package com.app.solarease.domain.usecase.auth

import com.app.solarease.common.Resource
import com.app.solarease.domain.repository.AuthRepository
import javax.inject.Inject

class SignOutUseCase  @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(): Resource<Unit> {
        return repository.signOut()
    }
}

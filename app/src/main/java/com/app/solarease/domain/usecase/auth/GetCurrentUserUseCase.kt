package com.app.solarease.domain.usecase.auth

import com.app.solarease.domain.model.User
import com.app.solarease.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(): Flow<User?> = repository.currentUser
}
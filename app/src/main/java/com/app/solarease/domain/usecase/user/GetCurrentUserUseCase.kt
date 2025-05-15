package com.app.solarease.domain.usecase.user

import com.app.solarease.common.Resource
import com.app.solarease.domain.model.User
import com.app.solarease.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(userId: String): Flow<Resource<User?>> =
        userRepository.getCurrentUser(userId)
}

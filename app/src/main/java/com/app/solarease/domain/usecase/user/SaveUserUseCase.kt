package com.app.solarease.domain.usecase.user

import com.app.solarease.common.Resource
import com.app.solarease.domain.model.User
import com.app.solarease.domain.repository.UserRepository
import javax.inject.Inject

class SaveUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(user: User): Resource<Unit> {
        return userRepository.saveUser(user)
    }
}

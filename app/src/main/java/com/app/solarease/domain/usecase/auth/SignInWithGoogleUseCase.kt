package com.app.solarease.domain.usecase.auth

import com.app.solarease.common.Resource
import com.app.solarease.domain.model.User
import com.app.solarease.domain.repository.AuthRepository
import javax.inject.Inject

class SignInWithGoogleUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(idToken: String): Resource<User> {
        return repository.signInWithGoogle(idToken)
    }
}

package com.app.solarease.domain.repository

import com.app.solarease.common.Resource
import com.app.solarease.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val currentUser: Flow<User?>
    suspend fun signInWithGoogle(idToken: String): Resource<User>
    suspend fun signOut(): Resource<Unit>
}

package com.app.solarease.data.repository

import com.app.solarease.common.Resource
import com.app.solarease.data.remote.FirestoreService
import com.app.solarease.domain.model.User
import com.app.solarease.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val firestore: FirestoreService
) : UserRepository {
    override suspend fun saveUser(user: User): Resource<Unit> = try {
        firestore.saveDocument("users", user.id, user)
        Resource.Success(Unit)
    } catch (e: Exception) {
        Resource.Error(e.message.orEmpty())
    }

    override fun getCurrentUser(userId: String): Flow<Resource<User?>> = flow {
        emit(Resource.Loading())
        try {
            val user = firestore.getDocumentById("users", userId) { snap ->
                snap.toObject(User::class.java)
            }
            emit(Resource.Success(user))
        } catch (e: Exception) {
            emit(Resource.Error(e.message.orEmpty()))
        }
    }
}

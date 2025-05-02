package com.app.solarease.presentation.auth

import com.app.solarease.domain.model.User

sealed class AuthState {
    data object Checking : AuthState()
    data class Authenticated(val user: User) : AuthState()
    data object Unauthenticated : AuthState()
    data class Error(val message: String) : AuthState()
}

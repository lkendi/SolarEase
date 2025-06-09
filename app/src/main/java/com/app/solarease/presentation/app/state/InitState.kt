package com.app.solarease.presentation.app.state

sealed class InitState {
    data object Loading : InitState()
    data object Authenticated : InitState()
    data object Unauthenticated : InitState()
    data class Error(val message: String) : InitState()
}


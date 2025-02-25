package com.example.solarease.presentation.navigation

sealed class NavigationEvent {
    data object NavigateToHome :  NavigationEvent()
}
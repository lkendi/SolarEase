package com.app.solarease.presentation.app

sealed class PermissionState {
    data object Idle : PermissionState()
    data object RequestPermission : PermissionState()
    data object Granted : PermissionState()
    data object Denied : PermissionState()
}

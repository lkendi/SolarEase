package com.app.solarease.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.solarease.common.Resource
import com.app.solarease.domain.model.Device
import com.app.solarease.domain.usecase.device.GetDevicesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeviceViewModel @Inject constructor(
    private val getDevicesUseCase: GetDevicesUseCase
) : ViewModel() {
    private val _devicesState = MutableStateFlow<Resource<List<Device>>>(Resource.Loading())
    val devicesState: StateFlow<Resource<List<Device>>> = _devicesState

    fun fetchDevices(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            _devicesState.value = Resource.Loading()
            _devicesState.value = getDevicesUseCase(forceRefresh)
        }
    }
}

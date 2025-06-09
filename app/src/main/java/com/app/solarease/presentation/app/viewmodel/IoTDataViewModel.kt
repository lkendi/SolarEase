package com.app.solarease.presentation.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.solarease.common.Resource
import com.app.solarease.domain.model.IoTData
import com.app.solarease.domain.usecase.iotdata.GetIoTDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class IoTDataViewModel @Inject constructor(
    private val getIoTDataUseCase: GetIoTDataUseCase,
    @Named("deviceId") private val deviceId: String
) : ViewModel() {

    sealed class UiState {
        data object Loading : UiState()
        data class Success(val data: Resource<IoTData>) : UiState()
        data class Error(val message: String) : UiState()
    }

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    init {
        loadIoTData()
    }

    private fun loadIoTData() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val result = getIoTDataUseCase(deviceId)
                _uiState.value = UiState.Success(result)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}

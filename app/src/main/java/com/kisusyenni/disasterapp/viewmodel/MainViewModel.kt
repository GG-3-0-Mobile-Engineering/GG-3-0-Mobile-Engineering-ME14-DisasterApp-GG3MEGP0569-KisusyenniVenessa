package com.kisusyenni.disasterapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kisusyenni.disasterapp.data.repository.DisasterAppRepository
import com.kisusyenni.disasterapp.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class MainViewModel(private val repository: DisasterAppRepository) : ViewModel() {

    private val _reports: MutableStateFlow<UiState> = MutableStateFlow(UiState.Empty)
    val reports: StateFlow<UiState> = _reports

    fun getReports(type: String?) = viewModelScope.launch {
       _reports.value = UiState.Loading
        repository.getReports(type).catch { e ->
            _reports.value = UiState.Failure(e)
        }.collect {data ->
            _reports.value = UiState.Success(data)
        }
    }

}
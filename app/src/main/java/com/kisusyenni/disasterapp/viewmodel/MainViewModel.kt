package com.kisusyenni.disasterapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kisusyenni.disasterapp.data.SettingsPreference
import com.kisusyenni.disasterapp.data.repository.DisasterAppRepository
import com.kisusyenni.disasterapp.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainViewModel(private val repository: DisasterAppRepository, private  val preferences: SettingsPreference) : ViewModel() {

    private val _reports: MutableStateFlow<UiState> = MutableStateFlow(UiState.Empty)
    val reports: StateFlow<UiState> = _reports

    private val _isDarkMode: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode

    fun getReports(type: String?) = viewModelScope.launch {
       _reports.value = UiState.Loading
        repository.getReports(type).catch { e ->
            _reports.value = UiState.Failure(e)
        }.collect {data ->
            _reports.value = UiState.Success(data)
        }
    }

    fun getTheme() {
        viewModelScope.launch {
            _isDarkMode.value = preferences.getTheme().first()
        }
    }

    fun saveTheme(isDarkMode: Boolean) {
        viewModelScope.launch {
            _isDarkMode.value = isDarkMode
            preferences.saveTheme(isDarkMode)
        }
    }
}
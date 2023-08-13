package com.kisusyenni.disasterapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kisusyenni.disasterapp.data.SettingsPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private  val preferences: SettingsPreference): ViewModel() {

    private val _isDarkMode: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode

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
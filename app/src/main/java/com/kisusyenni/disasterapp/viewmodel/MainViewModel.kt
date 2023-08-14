package com.kisusyenni.disasterapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kisusyenni.disasterapp.data.SettingsPreference
import com.kisusyenni.disasterapp.data.repository.DisasterAppRepository
import com.kisusyenni.disasterapp.utils.Area
import com.kisusyenni.disasterapp.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: DisasterAppRepository, private  val preferences: SettingsPreference) : ViewModel() {

    private val _reports: MutableStateFlow<UiState> = MutableStateFlow(UiState.Empty)
    val reports: StateFlow<UiState> = _reports

    private val _admin = MutableLiveData<Area?>()
    val admin = _admin

    private val _disasterType = MutableLiveData<String>()
    val disasterType = _disasterType

    private val _areaData = MutableLiveData<List<Area>>()
    val areaData = _areaData

    private val _isDarkMode: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode

    fun getTheme() {
        viewModelScope.launch {
            _isDarkMode.value = preferences.getTheme().first()
        }
    }

    fun getReports(disaster: String) = viewModelScope.launch {
       _reports.value = UiState.Loading
        repository.getReports(disaster).catch { e ->
            _reports.value = UiState.Failure(e)
        }.collect {data ->
            _reports.value = UiState.Success(data)
        }
    }

    fun getReports(disaster: String, admin: String) = viewModelScope.launch {
        _reports.value = UiState.Loading
        repository.getReports(disaster, admin).catch { e ->
            _reports.value = UiState.Failure(e)
        }.collect {data ->
            _reports.value = UiState.Success(data)
        }
    }

    fun setAdmin(area: Area?) {
        _admin.value = area
    }

    fun getAdmin(): Area? {
        return _admin.value
    }

    fun setDisasterType(type: String) {
        _disasterType.value = type
    }

    fun setAreaData(areaList: List<Area>) {
        _areaData.value = areaList
    }
}
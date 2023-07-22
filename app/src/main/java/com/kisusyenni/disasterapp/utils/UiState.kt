package com.kisusyenni.disasterapp.utils

sealed class UiState{
    object Loading: UiState()
    class Failure(val e: Throwable) : UiState()
    class Success<T: Any>(val result:T) : UiState()
    object Empty: UiState()
}
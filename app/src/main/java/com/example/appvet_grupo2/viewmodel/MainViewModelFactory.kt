package com.example.appvet_grupo2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appvet_grupo2.data.AppState

class MainViewModelFactory(
    private val appState: AppState
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(appState) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
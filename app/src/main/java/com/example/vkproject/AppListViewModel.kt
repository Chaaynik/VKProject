package com.example.vkproject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

sealed interface AppListEvent {
    data class ShowSnackbar(val message: String) : AppListEvent
}

class AppListViewModel : ViewModel() {

    private val _events = Channel<AppListEvent>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    init {
        viewModelScope.launch {
            println("Данные загружены один раз")
        }
    }

    fun onToolbarClick() {
        viewModelScope.launch {
            _events.send(AppListEvent.ShowSnackbar("Snackbar"))
        }
    }
}
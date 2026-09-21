package com.example.trippocket.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trippocket.data.model.Transport
import com.example.trippocket.data.repository.TransportRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransportsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: TransportRepository
) : ViewModel() {
    private val tripId: Long =
        checkNotNull(savedStateHandle.get<String>("tripId")).toLong()

    val transports: StateFlow<List<Transport>> =
        repository
            .getTripTransports(tripId)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )

    fun addTransport(transport: Transport) {
        viewModelScope.launch {
            repository.addTransport(transport)
        }
    }

    fun updateTransport(transport: Transport) {
        viewModelScope.launch {
            repository.updateTransport(transport)
        }
    }

    fun deleteTransport(id: Long) {
        viewModelScope.launch {
            repository.deleteTransport(id)
        }
    }
}
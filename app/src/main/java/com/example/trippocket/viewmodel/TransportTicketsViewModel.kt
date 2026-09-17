package com.example.trippocket.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trippocket.data.model.TransportTicket
import com.example.trippocket.data.repository.TransportTicketRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransportTicketsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: TransportTicketRepository
) : ViewModel() {
    private val tripId: Long =
        checkNotNull(savedStateHandle.get<String>("tripId")).toLong()

    val tickets: StateFlow<List<TransportTicket>> =
        repository
            .getTicketsForTrip(tripId)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )

    fun addTicket(ticket: TransportTicket) {
        viewModelScope.launch {
            repository.addTicket(ticket)
        }
    }

    fun deleteTicket(ticket: TransportTicket) {
        viewModelScope.launch {
            repository.deleteTicket(ticket)
        }
    }
}
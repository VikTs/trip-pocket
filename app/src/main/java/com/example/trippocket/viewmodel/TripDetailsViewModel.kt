package com.example.trippocket.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trippocket.data.model.Trip
import com.example.trippocket.data.model.TransportTicket
import com.example.trippocket.data.repository.TripRepository
import com.example.trippocket.data.repository.TransportTicketRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class TripDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    tripRepository: TripRepository,
    transportTicketRepository: TransportTicketRepository
) : ViewModel() {

    private val tripId: Long =
        checkNotNull(savedStateHandle.get<String>("tripId")).toLong()

    val trip: StateFlow<Trip?> =
        tripRepository
            .getTripById(tripId)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = null
            )

    val tickets: StateFlow<List<TransportTicket>> =
        transportTicketRepository
            .getTicketsForTrip(tripId)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )
}
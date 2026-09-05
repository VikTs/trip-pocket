package com.example.trippocket.viewmodel

import androidx.lifecycle.ViewModel
import com.example.trippocket.data.model.Trip
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class TripsViewModel : ViewModel() {
    private val _trips = MutableStateFlow<List<Trip>>(emptyList())

    val trips: StateFlow<List<Trip>> = _trips

    fun addTrip(trip: Trip) {
        _trips.update { it + trip }
    }
}
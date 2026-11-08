package com.viktoriia.trippocket.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.viktoriia.trippocket.data.model.Trip
import com.viktoriia.trippocket.data.model.TripEvent
import com.viktoriia.trippocket.data.repository.AccommodationRepository
import com.viktoriia.trippocket.data.repository.TransportRepository
import com.viktoriia.trippocket.data.repository.TripRepository
import com.viktoriia.trippocket.data.utils.TripEventFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TripsViewModel @Inject constructor(
    private val repository: TripRepository,
    private val transportRepository: TransportRepository,
    private val accommodationRepository: AccommodationRepository
) : ViewModel() {
    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> =
        _isLoading.asStateFlow()

    val trips: StateFlow<List<Trip>> =
        repository
            .getTrips()
            .onStart {
                _isLoading.value = true
            }
            .onEach {
                _isLoading.value = false
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )

    fun getTripById(tripId: Long): Flow<Trip?> {
        return repository.getTripById(tripId)
    }

    fun addTrip(trip: Trip) {
        viewModelScope.launch {
            repository.addTrip(trip)
        }
    }

    fun updateTrip(trip: Trip) {
        viewModelScope.launch {
            repository.updateTrip(trip)
        }
    }

    fun deleteTrip(tripId: Long) {
        viewModelScope.launch {
            repository.deleteTrip(tripId)
        }
    }

    fun getTripEvents(
        tripId: Long
    ): StateFlow<List<TripEvent>?> =
        observeTripEvents(tripId)
            .map<List<TripEvent>, List<TripEvent>?> { it }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = null
            )

    fun getTripEvents(
        tripIds: List<Long>
    ): Flow<Map<Long, List<TripEvent>>> {
        if (tripIds.isEmpty()) {
            return flowOf(emptyMap())
        }

        val eventFlows = tripIds.map { tripId ->
            observeTripEvents(tripId).map { events ->
                tripId to events
            }
        }

        return combine(eventFlows) { events ->
            events.toMap()
        }
    }

    private fun observeTripEvents(
        tripId: Long
    ): Flow<List<TripEvent>> =
        combine(
            transportRepository.getTripTransports(tripId),
            accommodationRepository.getForTrip(tripId)
        ) { transports, accommodations ->
            TripEventFactory.create(transports, accommodations)
        }.flowOn(Dispatchers.Default)
}
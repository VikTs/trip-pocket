package com.example.trippocket.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trippocket.data.model.Accommodation
import com.example.trippocket.data.repository.AccommodationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccommodationViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: AccommodationRepository
) : ViewModel() {
    private val _accommodation =
        MutableStateFlow<Accommodation?>(null)

    val accommodation =
        _accommodation.asStateFlow()

    private val tripId: Long =
        checkNotNull(
            savedStateHandle.get<String>("tripId")
        ).toLong()

    val accommodations: StateFlow<List<Accommodation>> =
        repository
            .getForTrip(tripId)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )

    fun loadAccommodation(
        accommodationId: Long
    ) {
        viewModelScope.launch {
            repository
                .getById(accommodationId)
                .collect { accommodation ->
                    _accommodation.value = accommodation
                }
        }
    }

    fun addAccommodation(
        tripId: Long,
        accommodation: Accommodation
    ) {
        viewModelScope.launch {
            repository.addAccommodation(
                accommodation.copy(
                    tripId = tripId
                )
            )
        }
    }

    fun updateAccommodation(
        accommodation: Accommodation
    ) {
        viewModelScope.launch {
            repository.updateAccommodation(accommodation)
        }
    }

    fun deleteAccommodation(
        accommodationId: Long
    ) {
        viewModelScope.launch {
            repository.deleteAccommodation(accommodationId)
        }
    }
}
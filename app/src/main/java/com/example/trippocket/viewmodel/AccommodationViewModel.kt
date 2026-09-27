package com.example.trippocket.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trippocket.data.model.Accommodation
import com.example.trippocket.data.repository.AccommodationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccommodationViewModel @Inject constructor(
    private val repository: AccommodationRepository
) : ViewModel() {
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
}
package com.example.trippocket.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trippocket.data.model.Accommodation
import com.example.trippocket.data.model.AccommodationNotification
import com.example.trippocket.data.repository.AccommodationNotificationRepository
import com.example.trippocket.data.repository.AccommodationRepository
import com.example.trippocket.notification.NotificationScheduler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccommodationViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: AccommodationRepository,
    private val notificationRepository: AccommodationNotificationRepository,
    private val notificationScheduler: NotificationScheduler
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
            val accommodationId =
                repository.addAccommodation(
                    accommodation.copy(
                        tripId = tripId
                    )
                )

            val notification = AccommodationNotification(
                accommodationId = accommodationId,
                enabled = true,
                minutesBefore = 60
            )

            val notificationId =
                notificationRepository.addNotification(
                    notification
                )

            val savedNotification =
                notification.copy(
                    id = notificationId
                )

            notificationScheduler.scheduleAccommodation(
                notification = savedNotification,
                accommodation = accommodation.copy(
                    id = accommodationId
                )
            )
        }
    }

    fun updateAccommodation(
        accommodation: Accommodation
    ) {
        viewModelScope.launch {
            val notification =
                notificationRepository
                    .getByAccommodationId(
                        accommodation.id
                    )
                    .firstOrNull()

            if (notification != null) {
                notificationScheduler.cancel(
                    notification.id
                )
            }

            repository.updateAccommodation(
                accommodation
            )

            if (notification?.enabled == true) {
                notificationScheduler.scheduleAccommodation(
                    notification = notification,
                    accommodation = accommodation
                )
            }
        }
    }

    fun deleteAccommodation(
        accommodationId: Long
    ) {
        viewModelScope.launch {
            val notification =
                notificationRepository
                    .getByAccommodationId(
                        accommodationId
                    )
                    .firstOrNull()

            if (notification != null) {
                notificationScheduler.cancel(
                    notification.id
                )
            }

            repository.deleteAccommodation(
                accommodationId
            )
        }
    }

    fun getNotification(
        accommodationId: Long
    ) =
        notificationRepository.getByAccommodationId(
            accommodationId
        )

    fun setNotificationEnabled(
        accommodation: Accommodation,
        enabled: Boolean
    ) {
        viewModelScope.launch {
            val notification =
                notificationRepository
                    .getByAccommodationId(
                        accommodation.id
                    )
                    .firstOrNull()
                    ?: return@launch

            notificationRepository.setEnabled(
                accommodationId = accommodation.id,
                enabled = enabled
            )

            if (enabled) {
                notificationScheduler.scheduleAccommodation(
                    notification = notification.copy(
                        enabled = true
                    ),
                    accommodation = accommodation
                )
            } else {
                notificationScheduler.cancel(
                    notification.id
                )
            }
        }
    }
}
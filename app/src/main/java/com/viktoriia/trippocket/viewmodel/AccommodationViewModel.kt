package com.viktoriia.trippocket.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.viktoriia.trippocket.data.model.Accommodation
import com.viktoriia.trippocket.data.model.AccommodationDocument
import com.viktoriia.trippocket.data.model.AccommodationNotification
import com.viktoriia.trippocket.data.model.AccommodationNotificationType
import com.viktoriia.trippocket.data.repository.AccommodationDocumentRepository
import com.viktoriia.trippocket.data.repository.AccommodationNotificationRepository
import com.viktoriia.trippocket.data.repository.AccommodationRepository
import com.viktoriia.trippocket.notification.NotificationScheduler
import com.viktoriia.trippocket.ui.components.inputs.SelectedDocument
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccommodationViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: AccommodationRepository,
    private val notificationRepository: AccommodationNotificationRepository,
    private val notificationScheduler: NotificationScheduler,
    private val accommodationDocumentRepository: AccommodationDocumentRepository
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

    fun getAccommodation(
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
        accommodation: Accommodation,
        documents: List<SelectedDocument>
    ) {
        viewModelScope.launch {
            val savedAccommodation =
                accommodation.copy(
                    tripId = tripId
                )

            val accommodationId =
                repository.addAccommodation(
                    savedAccommodation
                )

            val savedAccommodationWithId =
                savedAccommodation.copy(
                    id = accommodationId
                )

            documents.forEach { document ->
                accommodationDocumentRepository.addDocument(
                    AccommodationDocument(
                        accommodationId = accommodationId,
                        name = document.name,
                        path = document.path
                    )
                )
            }

            val notifications = listOf(
                AccommodationNotification(
                    accommodationId = accommodationId,
                    type = AccommodationNotificationType.CHECK_IN,
                    enabled = true,
                    minutesBefore = 60
                ),
                AccommodationNotification(
                    accommodationId = accommodationId,
                    type = AccommodationNotificationType.CHECK_OUT,
                    enabled = true,
                    minutesBefore = 60
                )
            )

            notifications.forEach { notification ->
                val notificationId =
                    notificationRepository.addNotification(
                        notification
                    )

                notificationScheduler.scheduleAccommodation(
                    notification = notification.copy(
                        id = notificationId
                    ),
                    accommodation = savedAccommodationWithId
                )
            }
        }
    }

    fun updateAccommodation(
        accommodation: Accommodation,
        documents: List<SelectedDocument>
    ) {
        viewModelScope.launch {
            val notifications =
                notificationRepository
                    .getByAccommodationId(
                        accommodation.id
                    )
                    .first()

            notifications?.forEach { notification ->
                notificationScheduler.cancel(
                    notification.id
                )
            }

            repository.updateAccommodation(
                accommodation
            )

            notifications?.forEach { notification ->
                if (notification.enabled) {
                    notificationScheduler.scheduleAccommodation(
                        notification = notification,
                        accommodation = accommodation
                    )
                }
            }

            accommodationDocumentRepository
                .deleteForAccommodation(
                    accommodation.id
                )

            documents.forEach { document ->
                accommodationDocumentRepository.addDocument(
                    AccommodationDocument(
                        accommodationId = accommodation.id,
                        name = document.name,
                        path = document.path
                    )
                )
            }
        }
    }

    fun deleteAccommodation(
        accommodationId: Long
    ) {
        viewModelScope.launch {
            val notifications =
                notificationRepository
                    .getByAccommodationId(
                        accommodationId
                    )
                    .first()

            notifications?.forEach { notification ->
                notificationScheduler.cancel(
                    notification.id
                )
            }

            repository.deleteAccommodation(
                accommodationId
            )
        }
    }

    fun getDocuments(
        accommodationId: Long
    ): Flow<List<AccommodationDocument>> =
        accommodationDocumentRepository
            .getForAccommodation(
                accommodationId
            )

    fun addDocument(
        accommodationId: Long,
        name: String,
        path: String
    ) {
        viewModelScope.launch {
            accommodationDocumentRepository.addDocument(
                AccommodationDocument(
                    accommodationId = accommodationId,
                    name = name,
                    path = path
                )
            )
        }
    }

    fun getNotifications(
        accommodationId: Long
    ) =
        notificationRepository
            .getByAccommodationId(
                accommodationId
            )

    fun updateNotification(
        notification: AccommodationNotification
    ) {
        viewModelScope.launch {
            val accommodation =
                repository
                    .getById(
                        notification.accommodationId
                    )
                    .firstOrNull()
                    ?: return@launch

            notificationRepository.updateNotification(
                notification
            )

            if (notification.enabled) {
                notificationScheduler.scheduleAccommodation(
                    notification = notification,
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
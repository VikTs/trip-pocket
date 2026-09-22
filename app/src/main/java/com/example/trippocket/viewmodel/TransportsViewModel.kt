package com.example.trippocket.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trippocket.data.model.Notification
import com.example.trippocket.data.model.Transport
import com.example.trippocket.data.repository.NotificationRepository
import com.example.trippocket.data.repository.TransportRepository
import com.example.trippocket.notification.NotificationScheduler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransportsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: TransportRepository,
    private val notificationRepository: NotificationRepository,
    private val notificationScheduler: NotificationScheduler
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
            val transportId = repository.addTransport(transport)

            val notification = Notification(
                transportId = transportId,
                enabled = true,
                minutesBefore = 60
            )

            val notificationId =
                notificationRepository.addNotification(notification)

            val savedNotification = notification.copy(
                id = notificationId
            )

            notificationScheduler.schedule(
                notification = savedNotification,
                transport = transport.copy(id = transportId)
            )
        }
    }

    fun updateTransport(transport: Transport) {
        viewModelScope.launch {
            val notification =
                notificationRepository
                    .getByTransportId(transport.id)
                    .firstOrNull()

            if (notification != null) {
                notificationScheduler.cancel(notification.id)
            }

            repository.updateTransport(transport)

            if (notification?.enabled == true) {
                notificationScheduler.schedule(
                    notification = notification,
                    transport = transport
                )
            }
        }
    }

    fun deleteTransport(id: Long) {
        viewModelScope.launch {
            val notification =
                notificationRepository
                    .getByTransportId(id)
                    .firstOrNull()

            if (notification != null) {
                notificationScheduler.cancel(notification.id)
                notificationRepository.deleteNotification(notification.id)
            }

            repository.deleteTransport(id)
        }
    }
}
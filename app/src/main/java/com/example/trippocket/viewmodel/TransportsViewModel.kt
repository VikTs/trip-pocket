package com.example.trippocket.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trippocket.data.model.Transport
import com.example.trippocket.data.model.TransportDocument
import com.example.trippocket.data.model.TransportNotification
import com.example.trippocket.data.repository.TransportDocumentRepository
import com.example.trippocket.data.repository.TransportNotificationRepository
import com.example.trippocket.data.repository.TransportRepository
import com.example.trippocket.notification.NotificationScheduler
import com.example.trippocket.ui.screens.add_transport.SelectedDocument
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
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
    private val notificationRepository: TransportNotificationRepository,
    private val notificationScheduler: NotificationScheduler,
    private val transportDocumentRepository: TransportDocumentRepository
) : ViewModel() {
    private val tripId: Long =
        checkNotNull(
            savedStateHandle.get<String>("tripId")
        ).toLong()

    val transports: StateFlow<List<Transport>> =
        repository
            .getTripTransports(tripId)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )

    fun addTransport(
        transport: Transport,
        documents: List<SelectedDocument>
    ) {
        viewModelScope.launch {
            val transportId =
                repository.addTransport(transport)

            documents.forEach { document ->
                transportDocumentRepository.addDocument(
                    TransportDocument(
                        transportId = transportId,
                        name = document.name,
                        path = document.path
                    )
                )
            }

            val notification = TransportNotification(
                transportId = transportId,
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

            notificationScheduler.scheduleTransport(
                notification = savedNotification,
                transport = transport.copy(
                    id = transportId
                )
            )
        }
    }

    fun updateTransport(
        transport: Transport,
        documents: List<SelectedDocument>
    ) {
        viewModelScope.launch {
            val notification =
                notificationRepository
                    .getByTransportId(transport.id)
                    .firstOrNull()

            if (notification != null) {
                notificationScheduler.cancel(
                    notification.id
                )
            }

            repository.updateTransport(
                transport
            )

            transportDocumentRepository.deleteForTransport(
                transport.id
            )

            documents.forEach { document ->
                transportDocumentRepository.addDocument(
                    TransportDocument(
                        transportId = transport.id,
                        name = document.name,
                        path = document.path
                    )
                )
            }

            if (notification?.enabled == true) {
                notificationScheduler.scheduleTransport(
                    notification = notification,
                    transport = transport
                )
            }
        }
    }

    fun deleteTransport(
        id: Long
    ) {
        viewModelScope.launch {
            val notification =
                notificationRepository
                    .getByTransportId(id)
                    .firstOrNull()

            if (notification != null) {
                notificationScheduler.cancel(
                    notification.id
                )

                notificationRepository.deleteNotification(
                    notification.id
                )
            }

            repository.deleteTransport(id)
        }
    }

    fun getDocuments(
        transportId: Long
    ): Flow<List<TransportDocument>> =
        transportDocumentRepository.getForTransport(
            transportId
        )

    fun addDocument(
        transportId: Long,
        name: String,
        path: String
    ) {
        viewModelScope.launch {
            transportDocumentRepository.addDocument(
                TransportDocument(
                    transportId = transportId,
                    name = name,
                    path = path
                )
            )
        }
    }

    fun deleteDocument(
        id: Long
    ) {
        viewModelScope.launch {
            transportDocumentRepository.deleteDocument(
                id
            )
        }
    }

    fun updateNotification(
        notification: TransportNotification
    ) {
        viewModelScope.launch {
            val transport =
                repository
                    .getById(notification.transportId)
                    .firstOrNull()
                    ?: return@launch

            notificationRepository.updateNotification(
                notification
            )

            if (notification.enabled) {
                notificationScheduler.scheduleTransport(
                    notification = notification,
                    transport = transport
                )
            } else {
                notificationScheduler.cancel(
                    notification.id
                )
            }
        }
    }

    fun getNotification(
        transportId: Long
    ): Flow<TransportNotification?> =
        notificationRepository.getByTransportId(transportId)
}
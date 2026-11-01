package com.viktoriia.trippocket.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.viktoriia.trippocket.data.model.Airport
import com.viktoriia.trippocket.data.repository.AirportRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AirportsViewModel @Inject constructor(
    repo: AirportRepository
) : ViewModel() {

    val airports: StateFlow<List<Airport>> = flow { emit(repo.getAll()) }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
}
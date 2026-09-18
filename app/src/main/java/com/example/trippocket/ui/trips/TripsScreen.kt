package com.example.trippocket.ui.trips

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.trippocket.data.model.Trip
import com.example.trippocket.ui.components.TopBar
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripsScreen(
    trips: List<Trip>,
    onAddTripClick: () -> Unit,
    onTripClick: (Trip) -> Unit
) {
    val colors = MaterialTheme.colorScheme

    Scaffold(
        topBar = {
            TopBar(title = "My trips")
        }
    ) { innerPadding ->
        if (trips.isEmpty()) {
            EmptyTrips(
                onAddTripClick = onAddTripClick,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            )
        } else {
            var showUpcoming by remember { mutableStateOf(true) }

            val today = LocalDate.now()

            val filteredTrips = trips
                .filter { trip ->
                    if (showUpcoming) {
                        !trip.endDate.isBefore(today)
                    } else {
                        trip.endDate.isBefore(today)
                    }
                }
                .sortedBy { it.startDate }

            val tripsByYear = filteredTrips.groupBy { it.startDate.year }

            val currentYear = today.year
            val showYears = tripsByYear.keys.any { it != currentYear }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                TripFilterToggle(
                    showUpcoming = showUpcoming,
                    onUpcomingClick = { showUpcoming = true },
                    onPastClick = { showUpcoming = false },
                    modifier = Modifier.padding(
                        horizontal = 24.dp,
                        vertical = 18.dp
                    )
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                TripsList(
                    tripsByYear = tripsByYear,
                    showYears = showYears,
                    onTripClick = onTripClick,
                    modifier = Modifier.weight(1f)
                )

                FloatingActionButton(
                    onClick = onAddTripClick,
                    containerColor = colors.primary,
                    shape = CircleShape,
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add trip"
                    )
                }

            }
        }
    }
}

package com.example.trippocket.ui.trips

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.trippocket.data.model.Trip

@Composable
fun TripsList(
    tripsByYear: Map<Int, List<Trip>>,
    showYears: Boolean,
    onTripClick: (Trip) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (tripsByYear.isEmpty()) {
            item {
                Box(
                    modifier = Modifier.fillParentMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No trips found",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
        } else {
            tripsByYear.forEach { (year, yearTrips) ->
                if (showYears) {
                    item {
                        Text(
                            text = year.toString(),
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }

                items(yearTrips) { trip ->
                    TripCard(
                        trip = trip,
                        onClick = { onTripClick(trip) }
                    )
                }
            }
        }
    }
}
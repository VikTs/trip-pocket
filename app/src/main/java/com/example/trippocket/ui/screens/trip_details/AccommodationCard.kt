package com.example.trippocket.ui.screens.trip_details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.trippocket.data.model.Accommodation
import com.example.trippocket.utils.formatTripDateTime
import com.example.trippocket.utils.formatTripTime
import com.example.trippocket.R

@Composable
fun AccommodationCard(
    accommodation: Accommodation,
    onClick: () -> Unit,
    isFirst: Boolean,
    isLast: Boolean,
    isActive: Boolean,
    isPrevActive: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(44.dp))

        TimelineNode(
            icon = Icons.Default.Hotel,
            isFirst = isFirst,
            isLast = isLast,
            isActive = isActive,
            isPrevActive = isPrevActive
        )

        Spacer(modifier = Modifier.width(12.dp))

        Card(
            modifier = Modifier.weight(1f),
            onClick = { onClick() },
        ) {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = accommodation.name,
                        style = MaterialTheme.typography.titleLarge
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "${stringResource(R.string.trip_details_accommodation_checkin_label)}: ${
                            formatTripTime(
                                accommodation.checkOut
                            )
                        }",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Text(
                        text = "${stringResource(R.string.trip_details_accommodation_checkout_label)}: ${
                            formatTripDateTime(
                                accommodation.checkOut
                            )
                        }",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}
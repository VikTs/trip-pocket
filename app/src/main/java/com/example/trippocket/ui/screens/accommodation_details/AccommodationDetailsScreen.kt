package com.example.trippocket.ui.screens.accommodation_details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.trippocket.data.model.Accommodation
import com.example.trippocket.ui.components.TopBar
import com.example.trippocket.utils.formatTripDate
import com.example.trippocket.utils.formatTripTime
import com.example.trippocket.R
import com.example.trippocket.ui.components.AddressLink

@Composable
fun AccommodationDetailsScreen(
    accommodation: Accommodation,
    onBackClick: () -> Unit,
    onEditClick: (accommodationId: Long) -> Unit,
    onDeleteClick: (accommodationId: Long) -> Unit
) {
    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(R.string.accommodation_details_title),
                onBackClick = onBackClick,
                actions = {
                    AccommodationActionsMenu(
                        accommodationId = accommodation.id,
                        onEdit = onEditClick,
                        onDelete = onDeleteClick
                    )
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.Top
            ) {
                Icon(
                    imageVector = Icons.Default.Hotel,
                    contentDescription = "Hotel",
                    modifier = Modifier
                        .padding(top = 2.dp)
                        .size(22.dp)
                )

                Spacer(
                    modifier = Modifier.width(12.dp)
                )

                Text(
                    text = accommodation.name,
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            DetailRow(
                label = stringResource(R.string.accommodation_details_address_label),
            ) {
                AddressLink(
                    address = accommodation.address,
                    additionalInfo = accommodation.name
                )
            }

            DetailRow(
                label = stringResource(R.string.accommodation_details_check_in_label),
                value = "${formatTripDate(accommodation.checkIn.toLocalDate())}, " +
                        formatTripTime(accommodation.checkIn)
            )

            DetailRow(
                label = stringResource(R.string.accommodation_details_check_out_label),
                value = "${formatTripDate(accommodation.checkOut.toLocalDate())}, " +
                        formatTripTime(accommodation.checkOut)
            )
        }
    }
}
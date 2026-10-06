package com.viktoriia.trippocket.ui.screens.trip_details

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.viktoriia.trippocket.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddToTripBottomSheet(
    onDismiss: () -> Unit,
    onAddTransportClick: () -> Unit,
    onAddAccommodationClick: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 24.dp
                )
        ) {
            Text(
                text = stringResource(R.string.add_to_trip_bottom_sheet_title),
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            ListItem(
                headlineContent = {
                    Text(stringResource(R.string.add_to_trip_bottom_sheet_transport_label))
                },
                leadingContent = {
                    Icon(
                        imageVector = Icons.Default.DirectionsBus,
                        contentDescription = null
                    )
                },
                colors = ListItemDefaults.colors(
                    containerColor = Color.Transparent
                ),
                modifier = Modifier.clickable {
                    onAddTransportClick()
                }
            )

            ListItem(
                headlineContent = {
                    Text(stringResource(R.string.add_to_trip_bottom_sheet_accommodation_label))
                },
                leadingContent = {
                    Icon(
                        imageVector = Icons.Default.Hotel,
                        contentDescription = null
                    )
                },
                colors = ListItemDefaults.colors(
                    containerColor = Color.Transparent
                ),
                modifier = Modifier.clickable {
                    onAddAccommodationClick()
                }
            )
        }
    }
}
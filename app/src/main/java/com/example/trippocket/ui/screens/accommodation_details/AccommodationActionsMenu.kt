package com.example.trippocket.ui.screens.accommodation_details

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun AccommodationActionsMenu(
    accommodationId: Long,
    onEdit: (Long) -> Unit,
    onDelete: (Long) -> Unit
) {
    val colorScheme = MaterialTheme.colorScheme
    var expanded by remember {
        mutableStateOf(false)
    }

    IconButton(
        onClick = {
            expanded = true
        }
    ) {
        Icon(
            imageVector = Icons.Default.MoreVert,
            contentDescription = "More",
            tint = colorScheme.onPrimary
        )
    }

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = {
            expanded = false
        }
    ) {
        DropdownMenuItem(
            text = {
                Text("Edit")
            },
            onClick = {
                expanded = false
                onEdit(accommodationId)
            }
        )

        DropdownMenuItem(
            text = {
                Text("Delete")
            },
            onClick = {
                expanded = false
                onDelete(accommodationId)
            }
        )
    }
}
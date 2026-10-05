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
import androidx.compose.ui.res.stringResource
import com.example.trippocket.R
import com.example.trippocket.ui.components.DeleteConfirmationBottomSheet

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

    var showDeleteSheet by remember {
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
                Text(stringResource(R.string.common_edit))
            },
            onClick = {
                expanded = false
                onEdit(accommodationId)
            }
        )

        DropdownMenuItem(
            text = {
                Text(stringResource(R.string.common_delete))
            },
            onClick = {
                expanded = false
                showDeleteSheet = true
            }
        )
    }

    if (showDeleteSheet) {
        DeleteConfirmationBottomSheet(
            onConfirm = {
                showDeleteSheet = false
                onDelete(accommodationId)
            },
            onDismiss = {
                showDeleteSheet = false
            },
            title = stringResource(
                R.string.accommodation_details_delete_confirmation_title
            )
        )
    }
}
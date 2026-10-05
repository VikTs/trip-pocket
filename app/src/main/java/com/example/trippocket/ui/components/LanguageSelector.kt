package com.example.trippocket.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.example.trippocket.R
import com.example.trippocket.utils.setAppLanguage

@Composable
fun LanguageSelector() {
    val context = LocalContext.current
    val colorScheme = MaterialTheme.colorScheme

    var expanded by remember {
        mutableStateOf(false)
    }

    Box {
        IconButton(
            onClick = {
                expanded = true
            }
        ) {
            Icon(
                imageVector = Icons.Default.Language,
                tint = colorScheme.surface,
                contentDescription = stringResource(
                    R.string.language_selector_content_description
                )
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
                    Text("English")
                },
                onClick = {
                    setAppLanguage(context, "en")
                    expanded = false
                }
            )

            DropdownMenuItem(
                text = {
                    Text("Українська")
                },
                onClick = {
                    setAppLanguage(context, "uk")
                    expanded = false
                }
            )
        }
    }
}
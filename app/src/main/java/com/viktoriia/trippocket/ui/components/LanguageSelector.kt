package com.viktoriia.trippocket.ui.components

import android.app.Activity
import android.content.Context
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
import androidx.compose.ui.text.font.FontWeight
import com.viktoriia.trippocket.R
import com.viktoriia.trippocket.utils.getAppLocale
import com.viktoriia.trippocket.utils.setAppLanguage

@Composable
fun LanguageSelector() {
    val context = LocalContext.current
    val colorScheme = MaterialTheme.colorScheme
    val currentLanguage =
        getAppLocale(context).language

    var expanded by remember {
        mutableStateOf(false)
    }

    val activity = context as? Activity

    fun onLanguageChanged() {
        activity?.recreate()
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
            LanguageOption(
                name = "English",
                currentLanguage = currentLanguage,
                languageTag = "en",
                context = context,
                onLanguageChanged = ::onLanguageChanged
            )

            LanguageOption(
                name = "Українська",
                currentLanguage = currentLanguage,
                languageTag = "uk",
                context = context,
                onLanguageChanged = ::onLanguageChanged
            )

            LanguageOption(
                name = "Italiano",
                currentLanguage = currentLanguage,
                languageTag = "it",
                context = context,
                onLanguageChanged = ::onLanguageChanged
            )

            LanguageOption(
                name = "Deutsch",
                currentLanguage = currentLanguage,
                languageTag = "de",
                context = context,
                onLanguageChanged = ::onLanguageChanged
            )

            LanguageOption(
                name = "Français",
                currentLanguage = currentLanguage,
                languageTag = "fr",
                context = context,
                onLanguageChanged = ::onLanguageChanged
            )
        }
    }
}

@Composable
fun LanguageOption(
    name: String,
    currentLanguage: String,
    languageTag: String,
    context: Context,
    onLanguageChanged: () -> Unit,
) {
    DropdownMenuItem(
        text = {
            Text(
                text = name,
                fontWeight = if (languageTag == currentLanguage) {
                    FontWeight.Bold
                } else {
                    FontWeight.Normal
                }
            )
        },
        onClick = {
            setAppLanguage(
                context = context,
                languageTag = languageTag
            )

            if (currentLanguage != languageTag) {
                onLanguageChanged()
            }
        }
    )
}
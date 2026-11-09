package com.viktoriia.trippocket.ui.components

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.viktoriia.trippocket.R
import com.viktoriia.trippocket.ui.utils.getAppLocale
import com.viktoriia.trippocket.ui.utils.setAppLanguage

private val languages = listOf(
    "Čeština" to "cs",
    "Deutsch" to "de",
    "English" to "en",
    "Español" to "es",
    "Français" to "fr",
    "Italiano" to "it",
    "Magyar" to "hu",
    "Polski" to "pl",
    "Português" to "pt",
    "Srpski" to "sr-Latn",
    "Türkçe" to "tr",
    "Українська" to "uk",
)

@Composable
fun LanguageSelector() {
    val context = LocalContext.current
    val colorScheme = MaterialTheme.colorScheme
    val currentLanguage = getAppLocale(context).toLanguageTag()

    var searchQuery by remember {
        mutableStateOf("")
    }

    val filteredLanguages = languages.filter { (name, _) ->
        name.contains(searchQuery.trim(), ignoreCase = true)
    }

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
                searchQuery = ""
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
            },
            modifier = Modifier
                .width(260.dp)
                .heightIn(max = 420.dp)
        ) {
            Column {
                SearchInput(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                )

                if (filteredLanguages.isEmpty()) {
                    Text(
                        text = stringResource(R.string.language_selector_search_empty),
                        style = MaterialTheme.typography.bodySmall,
                        color = colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(16.dp)
                    )
                } else {
                    Column(
                        modifier = Modifier
                            .heightIn(max = 340.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                        filteredLanguages.forEach { (name, languageTag) ->
                            LanguageOption(
                                name = name,
                                currentLanguage = currentLanguage,
                                languageTag = languageTag,
                                context = context,
                                onLanguageChanged = ::onLanguageChanged
                            )
                        }
                    }
                }
            }
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
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 6.dp),
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

@Composable
fun SearchInput(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(stringResource(R.string.language_selector_search_hint))
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null
            )
        },
        singleLine = true,
        modifier = modifier
    )
}
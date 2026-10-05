package com.example.trippocket.utils

import android.app.LocaleManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import java.util.Locale

fun setAppLanguage(
    context: Context,
    languageTag: String
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        context
            .getSystemService(LocaleManager::class.java)
            .applicationLocales =
            android.os.LocaleList.forLanguageTags(languageTag)
    } else {
        AppCompatDelegate.setApplicationLocales(
            LocaleListCompat.forLanguageTags(languageTag)
        )
    }
}

fun getAppLocale(context: Context): Locale {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        context.getSystemService(LocaleManager::class.java)
            .applicationLocales
            .get(0)
            ?: Locale.getDefault()
    } else {
        AppCompatDelegate.getApplicationLocales()
            .get(0)
            ?.let { Locale.forLanguageTag(it.toLanguageTag()) }
            ?: Locale.getDefault()
    }
}
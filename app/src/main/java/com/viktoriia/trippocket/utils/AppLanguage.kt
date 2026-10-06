package com.viktoriia.trippocket.utils

import android.app.LocaleManager
import android.content.Context
import android.os.Build
import android.os.LocaleList
import java.util.Locale

private const val PREFS_NAME = "app_preferences"
private const val LANGUAGE_KEY = "language"
private const val LANGUAGE_SELECTED_KEY = "language_selected"
private val supportedLanguages = setOf(
    "en",
    "uk",
    "it",
    "de",
    "fr"
)

fun setAppLanguage(
    context: Context,
    languageTag: String
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        context
            .getSystemService(LocaleManager::class.java)
            .applicationLocales =
            LocaleList.forLanguageTags(languageTag)

    } else {
        context
            .getSharedPreferences(
                PREFS_NAME,
                Context.MODE_PRIVATE
            )
            .edit()
            .putString(LANGUAGE_KEY, languageTag)
            .putBoolean(LANGUAGE_SELECTED_KEY, true)
            .apply()
    }
}

fun getAppLocale(
    context: Context
): Locale {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

        context
            .getSystemService(LocaleManager::class.java)
            .applicationLocales
            .get(0)
            ?: Locale.getDefault()

    } else {

        val languageTag =
            context
                .getSharedPreferences(
                    PREFS_NAME,
                    Context.MODE_PRIVATE
                )
                .getString(
                    LANGUAGE_KEY,
                    null
                )

        languageTag
            ?.let { Locale.forLanguageTag(it) }
            ?: Locale.getDefault()
    }
}

fun initializeAppLanguage(context: Context) {
    if (isAppLanguageSelected(context)) {
        return
    }

    val systemLanguage =
        Locale.getDefault().language

    val language =
        if (systemLanguage in supportedLanguages) {
            systemLanguage
        } else {
            "en"
        }

    setAppLanguage(
        context = context,
        languageTag = language
    )
}

fun isAppLanguageSelected(
    context: Context
): Boolean {
    return context
        .getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )
        .getBoolean(
            LANGUAGE_SELECTED_KEY,
            false
        )
}
package com.viktoriia.trippocket.ui.utils

import android.app.LocaleManager
import android.content.Context
import android.os.Build
import android.os.LocaleList
import java.util.Locale
import androidx.core.content.edit

private const val PREFS_NAME = "app_preferences"
private const val LANGUAGE_KEY = "language"
private const val LANGUAGE_SELECTED_KEY = "language_selected"
private val supportedLanguages = setOf(
    "ar",       // Arabic
    "zh-Hans",  // Chinese (Simplified)
    "zh-TW",    // Chinese (Traditional, Taiwan)
    "cs",       // Czech
    "en",       // English
    "fil",      // Filipino
    "fi",       // Finnish
    "fr",       // French
    "de",       // German
    "el",       // Greek
    "hi",       // Hindi
    "hu",       // Hungarian
    "id",       // Indonesian
    "it",       // Italian
    "ja",       // Japanese
    "kk",       // Kazakh
    "ko",       // Korean
    "pl",       // Polish
    "pt",       // Portuguese
    "ro",       // Romanian
    "sr-Latn",  // Serbian (Latin)
    "es",       // Spanish
    "th",       // Thai
    "tr",       // Turkish
    "uk",       // Ukrainian
    "vi"        // Vietnamese
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
            .edit {
                putString(LANGUAGE_KEY, languageTag)
                    .putBoolean(LANGUAGE_SELECTED_KEY, true)
            }
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

    val systemLocale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        context.resources.configuration.locales[0]
    } else {
        Locale.getDefault()
    }

    val systemLanguage = when (systemLocale.language) {
        "sr" -> "sr-Latn"

        "zh" -> {
            val isTraditionalChinese =
                systemLocale.script == "Hant" ||
                        systemLocale.country.uppercase() in setOf("TW", "HK", "MO")

            if (isTraditionalChinese) "zh-TW" else "zh-Hans"
        }

        else -> systemLocale.language
    }

    val language = systemLanguage
        .takeIf { it in supportedLanguages }
        ?: "en"

    setAppLanguage(
        context = context,
        languageTag = language
    )
}

fun isAppLanguageSelected(context: Context): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        val localeManager =
            context.getSystemService(LocaleManager::class.java)

        !localeManager.applicationLocales.isEmpty
    } else {
        context.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        ).getBoolean(LANGUAGE_SELECTED_KEY, false)
    }
}
package com.viktoriia.trippocket.ui.navigation

import androidx.navigation.NavBackStackEntry

fun NavBackStackEntry.longArgument(
    name: String
): Long? =
    arguments
        ?.getString(name)
        ?.toLongOrNull()
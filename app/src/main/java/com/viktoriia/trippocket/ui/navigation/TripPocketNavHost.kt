package com.viktoriia.trippocket.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.viktoriia.trippocket.viewmodel.TripsViewModel

@Composable
fun TripPocketNavHost(
    destination: String? = null
) {
    val navController = rememberNavController()
    val tripsViewModel: TripsViewModel = hiltViewModel()

    LaunchedEffect(destination) {
        if (!destination.isNullOrBlank()) {
            val tripId = Regex(
                """trip/(\d+)"""
            )
                .find(destination)
                ?.groupValues
                ?.get(1)

            if (tripId != null) {
                navController.navigate("trip/$tripId")

                navController.navigate(destination) {
                    launchSingleTop = true
                }
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = "trips"
    ) {
        tripNavGraph(
            navController = navController,
            trips = tripsViewModel.trips,
            tripsViewModel = tripsViewModel
        )

        accommodationNavGraph(
            navController = navController
        )

        transportNavGraph(
            navController = navController
        )
    }
}
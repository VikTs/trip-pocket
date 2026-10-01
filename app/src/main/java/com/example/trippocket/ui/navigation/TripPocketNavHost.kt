package com.example.trippocket.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.trippocket.viewmodel.TripsViewModel

@Composable
fun TripPocketNavHost() {
    val navController = rememberNavController()

    val tripsViewModel: TripsViewModel = hiltViewModel()

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
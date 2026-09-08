package com.example.trippocket.ui.navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.trippocket.ui.create_trip.CreateTripScreen
import com.example.trippocket.ui.trips.TripsScreen
import com.example.trippocket.viewmodel.TripsViewModel

@Composable
fun TripPocketNavHost() {
    val navController = rememberNavController()
    val tripsViewModel: TripsViewModel = hiltViewModel()

    val trips by tripsViewModel.trips.collectAsState()

    NavHost(
        navController = navController,
        startDestination = "trips"
    ) {
        composable("trips") {
            TripsScreen(
                trips = trips,
                onAddTripClick = {
                    navController.navigate("create_trip")
                }
            )
        }

        composable("create_trip") {
            CreateTripScreen(
                onTripCreated = { trip ->
                    tripsViewModel.addTrip(trip)
                    navController.popBackStack()
                },
                onCancel = {
                    navController.popBackStack()
                }
            )
        }
    }
}
package com.example.trippocket.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.trippocket.ui.add_transport.AddTransportScreen
import com.example.trippocket.ui.create_trip.CreateTripScreen
import com.example.trippocket.ui.trip_details.TripDetailsScreen
import com.example.trippocket.ui.trips.TripsScreen
import com.example.trippocket.viewmodel.TripDetailsViewModel
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
                },
                onTripClick = { trip ->
                    navController.navigate("trip/${trip.id}")
                }
            )
        }

        composable("create_trip") {
            CreateTripScreen(
                onTripSaved = { trip ->
                    tripsViewModel.addTrip(trip)
                    navController.popBackStack()
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable("edit_trip/{tripId}") { backStackEntry ->
            val tripId = backStackEntry
                .arguments
                ?.getString("tripId")
                ?.toLongOrNull()

            if (tripId != null) {
                val trip by tripsViewModel
                    .getTripById(tripId)
                    .collectAsStateWithLifecycle(
                        initialValue = null,
                        lifecycle = LocalLifecycleOwner.current.lifecycle
                    )

                CreateTripScreen(
                    editTrip = trip,
                    onTripSaved = { trip ->
                        tripsViewModel.updateTrip(trip)
                        navController.popBackStack()
                    },
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }
        }

        composable("trip/{tripId}") { backStackEntry ->
            val tripId = backStackEntry
                .arguments
                ?.getString("tripId")
                ?.toLongOrNull()

            if (tripId != null) {
                val viewModel: TripDetailsViewModel = hiltViewModel()

                TripDetailsScreen(
                    onEditClick = { tripId ->
                        navController.navigate("edit_trip/${tripId}")
                    },
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onAddTransportClick = {
                        navController.navigate(
                            "trip/$tripId/add_transport"
                        )
                    },
                    viewModel = viewModel
                )
            }
        }

        composable("trip/{tripId}/add_transport") { backStackEntry ->
            val tripId = backStackEntry
                .arguments
                ?.getString("tripId")
                ?.toLongOrNull()

            if (tripId != null) {
                val tripDetailsEntry = remember(backStackEntry) {
                    navController.getBackStackEntry("trip/$tripId")
                }

                val viewModel: TripDetailsViewModel =
                    hiltViewModel(tripDetailsEntry)

                AddTransportScreen(
                    tripId = tripId,
                    onBackClick = {
                        navController.popBackStack()
                    },
                    viewModel = viewModel
                )
            }
        }
    }
}
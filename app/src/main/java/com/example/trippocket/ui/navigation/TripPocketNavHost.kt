package com.example.trippocket.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.trippocket.ui.screens.add_transport.AddTransportScreen
import com.example.trippocket.ui.screens.create_trip.CreateTripScreen
import com.example.trippocket.ui.screens.transport_details.TransportDetailsScreen
import com.example.trippocket.ui.screens.trip_details.TripDetailsScreen
import com.example.trippocket.ui.screens.trips.TripsScreen
import com.example.trippocket.viewmodel.TransportsViewModel
import com.example.trippocket.viewmodel.TripsViewModel

@Composable
fun TripPocketNavHost() {
    val navController = rememberNavController()

    val tripsViewModel: TripsViewModel = hiltViewModel()
    val trips by tripsViewModel.trips.collectAsStateWithLifecycle()

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

            val trip = trips.firstOrNull { it.id == tripId }

            if (tripId != null && trip != null) {
                val transportViewModel: TransportsViewModel =
                    hiltViewModel()

                val transports by transportViewModel.transports.collectAsStateWithLifecycle()

                TripDetailsScreen(
                    trip = trip,
                    transports = transports,
                    onEditClick = {
                        navController.navigate("edit_trip/$tripId")
                    },
                    onDeleteClick = {
                        tripsViewModel.deleteTrip(tripId)
                        navController.popBackStack()
                    },
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onAddTransportClick = {
                        navController.navigate(
                            "trip/$tripId/add_transport"
                        )
                    },
                    onTransportClick = { transportId ->
                        navController.navigate(
                            "trip/$tripId/transport/$transportId"
                        )
                    }
                )
            }
        }

        composable("trip/{tripId}/add_transport") { backStackEntry ->
            val tripId = backStackEntry
                .arguments
                ?.getString("tripId")
                ?.toLongOrNull()

            if (tripId != null) {
                val tripEntry = remember(backStackEntry) {
                    navController.getBackStackEntry("trip/$tripId")
                }

                val viewModel: TransportsViewModel =
                    hiltViewModel(tripEntry)

                AddTransportScreen(
                    tripId = tripId,
                    onBackClick = {
                        navController.popBackStack()
                    },
                    viewModel = viewModel
                )
            }
        }

        composable("trip/{tripId}/edit_transport/{transportId}") { backStackEntry ->
            val tripId = backStackEntry
                .arguments
                ?.getString("tripId")
                ?.toLongOrNull()

            val transportId = backStackEntry
                .arguments
                ?.getString("transportId")
                ?.toLongOrNull()

            if (tripId != null && transportId != null) {
                val tripEntry = remember(backStackEntry) {
                    navController.getBackStackEntry("trip/$tripId")
                }

                val viewModel: TransportsViewModel =
                    hiltViewModel(tripEntry)

                val transports by viewModel.transports
                    .collectAsStateWithLifecycle()

                val transport = transports
                    .firstOrNull { it.id == transportId }

                if (transport != null) {
                    AddTransportScreen(
                        tripId = tripId,
                        transportId = transportId,
                        onBackClick = {
                            navController.popBackStack()
                        },
                        viewModel = viewModel
                    )
                }
            }
        }

        composable("trip/{tripId}/transport/{transportId}") { backStackEntry ->
            val tripId = backStackEntry
                .arguments
                ?.getString("tripId")
                ?.toLongOrNull()

            val transportId = backStackEntry
                .arguments
                ?.getString("transportId")
                ?.toLongOrNull()

            if (tripId != null && transportId != null) {
                val tripEntry = remember(backStackEntry) {
                    navController.getBackStackEntry("trip/$tripId")
                }

                val transportViewModel: TransportsViewModel =
                    hiltViewModel(tripEntry)

                val transports by transportViewModel.transports
                    .collectAsStateWithLifecycle()

                val transport = transports
                    .firstOrNull { it.id == transportId }

                if (transport != null) {
                    TransportDetailsScreen(
                        transport = transport,
                        onBackClick = {
                            navController.popBackStack()
                        },
                        onEditClick = {
                            navController.navigate("trip/$tripId/edit_transport/$transportId")
                        },
                        onDeleteClick = {
                            transportViewModel.deleteTransport(transportId)
                            navController.popBackStack()
                        },
                    )
                }
            }
        }
    }
}
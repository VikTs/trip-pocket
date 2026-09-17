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
import com.example.trippocket.ui.transport_ticket_details.TransportTicketDetailsScreen
import com.example.trippocket.ui.trip_details.TripDetailsScreen
import com.example.trippocket.ui.trips.TripsScreen
import com.example.trippocket.viewmodel.TransportTicketsViewModel
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
                val transportTicketsViewModel: TransportTicketsViewModel =
                    hiltViewModel()

                val tickets by transportTicketsViewModel.tickets.collectAsStateWithLifecycle()

                TripDetailsScreen(
                    trip = trip,
                    tickets = tickets,
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

                val viewModel: TransportTicketsViewModel =
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

                val viewModel: TransportTicketsViewModel =
                    hiltViewModel(tripEntry)

                val tickets by viewModel.tickets
                    .collectAsStateWithLifecycle()

                val transportTicket = tickets
                    .firstOrNull { it.id == transportId }

                if (transportTicket != null) {
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

                val transportTicketsViewModel: TransportTicketsViewModel =
                    hiltViewModel(tripEntry)

                val transportTickets by transportTicketsViewModel.tickets
                    .collectAsStateWithLifecycle()

                val transportTicket = transportTickets
                    .firstOrNull { it.id == transportId }

                if (transportTicket != null) {
                    TransportTicketDetailsScreen(
                        transportTicket = transportTicket,
                        onBackClick = {
                            navController.popBackStack()
                        },
                        onEditClick = {
                            navController.navigate("trip/$tripId/edit_transport/$transportId")
                        },
                        onDeleteClick = {
                            transportTicketsViewModel.deleteTicket(transportId)
                            navController.popBackStack()
                        },
                    )
                }
            }
        }
    }
}
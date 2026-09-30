package com.example.trippocket.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.trippocket.data.model.Trip
import com.example.trippocket.ui.screens.create_trip.CreateTripScreen
import com.example.trippocket.ui.screens.trip_details.TripDetailsScreen
import com.example.trippocket.ui.screens.trips.TripsScreen
import com.example.trippocket.viewmodel.AccommodationViewModel
import com.example.trippocket.viewmodel.TransportsViewModel
import com.example.trippocket.viewmodel.TripsViewModel

fun NavGraphBuilder.tripNavGraph(
    navController: NavHostController,
    trips: List<Trip>,
    tripsViewModel: TripsViewModel
) {
    composable("trips") {
        TripsScreen(
            trips = trips,
            onAddTripClick = {
                navController.navigate("create_trip")
            },
            onTripClick = { trip ->
                navController.navigate(
                    "trip/${trip.id}"
                )
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

        val tripId =
            backStackEntry.longArgument("tripId")

        if (tripId != null) {
            val trip by tripsViewModel
                .getTripById(tripId)
                .collectAsStateWithLifecycle(
                    initialValue = null
                )

            CreateTripScreen(
                editTrip = trip,
                onTripSaved = { updatedTrip ->
                    tripsViewModel.updateTrip(updatedTrip)
                    navController.popBackStack()
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }

    composable("trip/{tripId}") { backStackEntry ->

        val tripId =
            backStackEntry.longArgument("tripId")

        val trip = trips.firstOrNull {
            it.id == tripId
        }

        if (tripId != null && trip != null) {

            val transportViewModel: TransportsViewModel =
                hiltViewModel()

            val transports by transportViewModel
                .transports
                .collectAsStateWithLifecycle()

            val accommodationViewModel: AccommodationViewModel =
                hiltViewModel()

            val accommodations by accommodationViewModel
                .accommodations
                .collectAsStateWithLifecycle()

            TripDetailsScreen(
                trip = trip,
                transports = transports,
                accommodations = accommodations,

                onEditClick = {
                    navController.navigate(
                        "edit_trip/$tripId"
                    )
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

                onAddAccommodationClick = {
                    navController.navigate(
                        "trip/$tripId/add_accommodation"
                    )
                },

                onTransportClick = { transportId ->
                    navController.navigate(
                        "trip/$tripId/transport/$transportId"
                    )
                },

                onAccommodationClick = { accommodationId ->
                    navController.navigate(
                        "trip/$tripId/accommodation/$accommodationId"
                    )
                }
            )
        }
    }
}
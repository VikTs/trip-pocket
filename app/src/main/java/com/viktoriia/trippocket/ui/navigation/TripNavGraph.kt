package com.viktoriia.trippocket.ui.navigation

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.viktoriia.trippocket.data.model.Trip
import com.viktoriia.trippocket.ui.screens.create_trip.CreateTripScreen
import com.viktoriia.trippocket.ui.screens.trip_details.TripDetailsScreen
import com.viktoriia.trippocket.ui.screens.trips.TripsScreen
import com.viktoriia.trippocket.viewmodel.AccommodationViewModel
import com.viktoriia.trippocket.viewmodel.TransportsViewModel
import com.viktoriia.trippocket.viewmodel.TripsViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.StateFlow

fun NavGraphBuilder.tripNavGraph(
    navController: NavHostController,
    trips: StateFlow<List<Trip>>,
    tripsViewModel: TripsViewModel
) {
    composable("trips") {

        val tripList by trips
            .collectAsStateWithLifecycle()

        TripsScreen(
            trips = tripList,
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

        val tripId = backStackEntry.arguments
            ?.getString("tripId")
            ?.toLongOrNull()

        if (tripId != null) {

            val trip by tripsViewModel
                .getTripById(tripId)
                .collectAsStateWithLifecycle(
                    initialValue = null
                )

            CreateTripScreen(
                editTrip = trip,
                onTripSaved = { updatedTrip ->
                    tripsViewModel.updateTrip(
                        updatedTrip
                    )

                    navController.popBackStack()
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }

    composable("trip/{tripId}") { backStackEntry ->

        val tripId = backStackEntry.arguments
            ?.getString("tripId")
            ?.toLongOrNull()

        val tripList by trips
            .collectAsStateWithLifecycle()

        val trip = tripList.firstOrNull {
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
                    tripsViewModel.deleteTrip(
                        tripId
                    )

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
package com.viktoriia.trippocket.ui.navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.viktoriia.trippocket.data.model.Trip
import com.viktoriia.trippocket.data.model.TripEvent
import com.viktoriia.trippocket.ui.screens.create_trip.CreateTripScreen
import com.viktoriia.trippocket.ui.screens.trip_details.TripDetailsScreen
import com.viktoriia.trippocket.ui.screens.trips.TripsScreen
import com.viktoriia.trippocket.viewmodel.TripsViewModel
import kotlinx.coroutines.flow.StateFlow

fun NavGraphBuilder.tripNavGraph(
    navController: NavHostController,
    trips: StateFlow<List<Trip>>,
    tripsViewModel: TripsViewModel
) {
    composable("trips") {
        val tripList by trips
            .collectAsStateWithLifecycle()

        val isLoading by tripsViewModel
            .isLoading
            .collectAsStateWithLifecycle()

        TripsScreen(
            trips = tripList,
            isLoading = isLoading,
            onAddTripClick = {
                navController.navigate("create_trip")
            },
            onTripClick = { trip ->
                navController.navigate("trip/${trip.id}")
            },
            onNextEventClick = { tripId, event ->
                navController.navigate("trip/$tripId")
                when (event) {
                    is TripEvent.TransportItem ->
                        navController.navigate("trip/$tripId/transport/${event.id}")
                    is TripEvent.AccommodationCheckInItem,
                    is TripEvent.AccommodationCheckOutItem ->
                        navController.navigate("trip/$tripId/accommodation/${event.id}")
                }
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
            val tripEventsFlow = remember(tripId) {
                tripsViewModel.getTripEvents(tripId)
            }

            val tripEvents by tripEventsFlow.collectAsStateWithLifecycle(
                lifecycle = LocalLifecycleOwner.current.lifecycle
            )

            TripDetailsScreen(
                trip = trip,
                tripEvents = tripEvents.orEmpty(),
                isLoading = tripEvents == null,
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
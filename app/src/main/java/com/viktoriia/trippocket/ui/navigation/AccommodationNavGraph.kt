package com.viktoriia.trippocket.ui.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.viktoriia.trippocket.ui.screens.accommodation_details.AccommodationDetailsScreen
import com.viktoriia.trippocket.ui.screens.add_accommodation.AddAccommodationScreen
import com.viktoriia.trippocket.viewmodel.AccommodationViewModel

fun NavGraphBuilder.accommodationNavGraph(
    navController: NavHostController
) {
    composable(
        "trip/{tripId}/add_accommodation"
    ) { backStackEntry ->

        val tripId =
            backStackEntry.longArgument("tripId")

        if (tripId != null) {

            val viewModel: AccommodationViewModel =
                hiltViewModel()

            AddAccommodationScreen(
                tripId = tripId,
                viewModel = viewModel,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }

    composable(
        "trip/{tripId}/accommodation/{accommodationId}/edit"
    ) { backStackEntry ->

        val tripId =
            backStackEntry.longArgument("tripId")

        val accommodationId =
            backStackEntry.longArgument("accommodationId")

        if (tripId != null && accommodationId != null) {

            val viewModel: AccommodationViewModel =
                hiltViewModel()

            val accommodation by viewModel
                .accommodation
                .collectAsStateWithLifecycle()

            LaunchedEffect(accommodationId) {
                viewModel.getAccommodation(
                    accommodationId
                )
            }

            accommodation?.let { existingAccommodation ->

                AddAccommodationScreen(
                    tripId = tripId,
                    accommodation = existingAccommodation,
                    onBackClick = {
                        navController.popBackStack()
                    },
                    viewModel = viewModel
                )
            }
        }
    }

    composable(
        "trip/{tripId}/accommodation/{accommodationId}"
    ) { backStackEntry ->

        val tripId =
            backStackEntry.longArgument("tripId")

        val accommodationId =
            backStackEntry.longArgument("accommodationId")

        if (tripId != null && accommodationId != null) {

            val viewModel: AccommodationViewModel =
                hiltViewModel()

            val accommodation by viewModel
                .accommodation
                .collectAsStateWithLifecycle()

            LaunchedEffect(accommodationId) {
                viewModel.getAccommodation(
                    accommodationId
                )
            }

            val notifications by viewModel
                .getNotifications(accommodationId)
                .collectAsStateWithLifecycle(
                    initialValue = null
                )

            val documents by viewModel
                .getDocuments(accommodationId)
                .collectAsStateWithLifecycle(
                    initialValue = emptyList()
                )

            accommodation?.let {

                AccommodationDetailsScreen(
                    accommodation = it,
                    notifications = notifications,
                    documents = documents,
                    viewModel = viewModel,

                    onBackClick = {
                        navController.popBackStack()
                    },

                    onEditClick = { id ->
                        navController.navigate(
                            "trip/$tripId/accommodation/$id/edit"
                        )
                    },

                    onDeleteClick = {
                        viewModel.deleteAccommodation(
                            accommodationId
                        )
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}
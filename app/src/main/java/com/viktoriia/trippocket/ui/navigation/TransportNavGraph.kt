package com.viktoriia.trippocket.ui.navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.viktoriia.trippocket.ui.screens.add_transport.AddTransportScreen
import com.viktoriia.trippocket.ui.screens.transport_details.TransportDetailsScreen
import com.viktoriia.trippocket.viewmodel.TransportsViewModel

fun NavGraphBuilder.transportNavGraph(
    navController: NavHostController
) {

    composable(
        "trip/{tripId}/add_transport"
    ) { backStackEntry ->

        val tripId =
            backStackEntry.longArgument("tripId")

        if (tripId != null) {

            val tripEntry = remember(backStackEntry) {
                navController.getBackStackEntry(
                    "trip/$tripId"
                )
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

    composable(
        "trip/{tripId}/edit_transport/{transportId}"
    ) { backStackEntry ->

        val tripId =
            backStackEntry.longArgument("tripId")

        val transportId =
            backStackEntry.longArgument("transportId")

        if (tripId != null && transportId != null) {

            val tripEntry = remember(backStackEntry) {
                navController.getBackStackEntry(
                    "trip/$tripId"
                )
            }

            val viewModel: TransportsViewModel =
                hiltViewModel(tripEntry)

            val transports by viewModel
                .transports
                .collectAsStateWithLifecycle()

            val transport = transports
                .firstOrNull {
                    it.id == transportId
                }

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

    composable(
        "trip/{tripId}/transport/{transportId}"
    ) { backStackEntry ->

        val tripId =
            backStackEntry.longArgument("tripId")

        val transportId =
            backStackEntry.longArgument("transportId")

        if (tripId != null && transportId != null) {

            val tripEntry = remember(backStackEntry) {
                navController.getBackStackEntry(
                    "trip/$tripId"
                )
            }

            val viewModel: TransportsViewModel =
                hiltViewModel(tripEntry)

            val transports by viewModel
                .transports
                .collectAsStateWithLifecycle()

            val transport = transports
                .firstOrNull {
                    it.id == transportId
                }

            val documents by viewModel
                .getDocuments(transportId)
                .collectAsStateWithLifecycle(
                    initialValue = emptyList()
                )

            val notification by viewModel
                .getNotification(transportId)
                .collectAsStateWithLifecycle(
                    initialValue = null
                )

            if (transport != null) {

                TransportDetailsScreen(
                    transport = transport,
                    documents = documents,
                    viewModel = viewModel,
                    notification = notification,

                    onBackClick = {
                        navController.popBackStack()
                    },

                    onEditClick = {
                        navController.navigate(
                            "trip/$tripId/edit_transport/$transportId"
                        )
                    },

                    onDeleteClick = {
                        viewModel.deleteTransport(
                            transportId
                        )
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}
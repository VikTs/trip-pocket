package com.viktoriia.trippocket.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Airport(
    @SerialName("iata_code") val iataCode: String,
    val name: String
)
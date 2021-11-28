package com.example.truckassignment.Model

data class LastRunningState(
    val lat: Double,
    val lng: Double,
    val stopNotficationSent: Int,
    val stopStartTime: Long,
    val truckId: Int,
    val truckRunningState: Int
)
package com.example.truckassignment.Api

import com.example.truckassignment.Model.TruckModel
import retrofit2.Response
import retrofit2.http.GET

interface TruckServices
{
    @GET("/tt/mobile/logistics/searchTrucks?auth-company=PCH&companyId=33&deactivated=false&key=g2qb5jvucg7j8skpu5q7ria0mu&q-expand=true&q-include=lastRunningState,lastWaypoint")

    suspend fun getTruckData():Response<TruckModel>
}
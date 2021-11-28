package com.example.truckassignment

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import com.example.truckassignment.Api.RetrofitInstance
import com.example.truckassignment.Api.TruckServices
import com.example.truckassignment.Model.Data
import com.example.truckassignment.Model.TruckModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import retrofit2.Response


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var retService: TruckServices

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        actionBar?.title = "Truck"

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val sydney = LatLng(-34.0, 151.0)
        retService = RetrofitInstance.getRetrofitInstance().create(TruckServices::class.java)
        val responseLiveData: LiveData<Response<TruckModel>> = liveData {
            val response = retService.getTruckData()
            emit(response)
        }
        responseLiveData.observe(this, Observer {
           var data = it.body()!!.data
            for(i in 0..data.size-1)
            {
                var dataItem = data.get(i)
                var lat  =dataItem.lastWaypoint.lat
                var long = dataItem.lastWaypoint.lng
                if (dataItem.lastRunningState.truckRunningState.equals(0))
                {
                    mMap.addMarker(MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)).position(LatLng(lat,long)).title("Marker"))
                }
                else if(dataItem.lastRunningState.truckRunningState.equals(1) && dataItem.lastWaypoint.ignitionOn.equals("false"))
                {
                    mMap.addMarker(MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)).position(LatLng(lat,long)).title("Marker"))
                }else if(dataItem.lastRunningState.truckRunningState.equals(1) && dataItem.lastWaypoint.ignitionOn.equals("true"))
                {
                    mMap.addMarker(MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)).position(LatLng(lat,long)).title("Marker"))
                }
                else
                {
                    mMap.addMarker(MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)).position(LatLng(lat,long)).title("Marker"))
                }
                mMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(lat,long)))
            }
        })



    }
}
package com.glovo.glovo.map.view

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import com.glovo.glovo.R
import com.glovo.glovo.base.view.MvpActivity
import com.glovo.glovo.map.data.dto.City
import com.glovo.glovo.map.data.dto.Country
import com.glovo.glovo.map.presenter.MapPresenter
import com.google.android.gms.common.util.MapUtils
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolygonOptions
import com.google.maps.android.PolyUtil
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import java.util.ArrayList


private val TAG = "TAG${MapActivity::class.java.simpleName}"

class MapActivity : MvpActivity<MainView, MapPresenter>(), MainView, OnMapReadyCallback {


    override val presenter: MapPresenter by inject { parametersOf(this) }

    private lateinit var mMap: GoogleMap


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

        presenter.getCities()


    }


    override fun showLoading() {
        Log.d(TAG, "showLoading")

    }


    override fun hideLoading() {
        Log.d(TAG, "hideLoading")

    }

    override fun showCountries(countries: List<Country>) {
        Log.d(TAG, "showCountries ${countries.size}")

    }

    override fun showError(error: String) {
        Log.d(TAG, "showError")
    }

    override fun showCities(cities: List<City>) {
        Log.d(TAG, "showCities ${cities.size}")





    }

    override fun showCityDetails(city: City) {
        Log.d(TAG, "showCityDetails ${city.name}")

    }

}

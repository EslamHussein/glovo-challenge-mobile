package com.glovo.glovo.map.view

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import com.glovo.glovo.R
import com.glovo.glovo.base.view.MvpActivity
import com.glovo.glovo.ext.ConvexHull
import com.glovo.glovo.map.data.dto.City
import com.glovo.glovo.map.data.dto.Country
import com.glovo.glovo.map.presenter.MapPresenter
import com.glovo.glovo.map.view.cluster.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolygonOptions
import com.google.maps.android.PolyUtil
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import java.util.ArrayList
import com.google.maps.android.clustering.ClusterManager


private val TAG = "TAG${MapActivity::class.java.simpleName}"

class MapActivity : MvpActivity<MainView, MapPresenter>(), MainView, OnMapReadyCallback,
    ClusterManager.OnClusterItemClickListener<CityClusterItem> {

    override val presenter: MapPresenter by inject { parametersOf(this) }

    private lateinit var mMap: GoogleMap
    private var mClusterItemManager: ClusterManager<CityClusterItem>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mClusterItemManager = ClusterManager(this, mMap)
        mClusterItemManager?.algorithm = ClusterAlgorithm()
        val renderer = CitiesClusterRenderer(this, mMap, mClusterItemManager!!)
        mClusterItemManager?.renderer = renderer
        mMap.setOnCameraIdleListener(mClusterItemManager)
        mMap.setOnMarkerClickListener(mClusterItemManager)
        mClusterItemManager?.setOnClusterItemClickListener(this)
        presenter.getCities()

    }


    override fun showLoading() {



    }


    override fun hideLoading() {


    }

    override fun showCountries(countries: List<Country>) {


    }

    override fun showError(error: String) {



    }

    override fun showCities(cities: List<City>) {

        for (city in cities) {

            val polygons = ArrayList<PolygonOptions>()
            val polygonOptions = PolygonOptions()

            for (polygonEncoded in city.workingArea) {

                polygonOptions.addAll(PolyUtil.decode(polygonEncoded))
                polygons.add(polygonOptions)
            }

            val polygonOption = ConvexHull.convert(polygons).strokeColor(Color.RED)?.fillColor(Color.MAGENTA)
            val polygon = mMap.addPolygon(polygonOption)

            mClusterItemManager?.addItem(
                CityClusterItem(
                    polygon?.getCenterPoint()!!,
                    city.name,
                    city.countryCode,
                    city.countryCode, polygon.getBounds()
                )
            )
        }

    }

    override fun onClusterItemClick(pin: CityClusterItem?): Boolean {

        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(pin?.bounds, 0))
        return true
    }


    override fun showCityDetails(city: City) {
        Log.d(TAG, "showCityDetails ${city.name}")

    }

}

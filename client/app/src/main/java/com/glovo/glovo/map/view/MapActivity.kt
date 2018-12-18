package com.glovo.glovo.map.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import com.glovo.glovo.R
import com.glovo.glovo.base.view.MvpActivity
import com.glovo.glovo.ext.ConvexHull
import com.glovo.glovo.ext.getOkDialog
import com.glovo.glovo.map.data.dto.City
import com.glovo.glovo.map.data.dto.Country
import com.glovo.glovo.map.presenter.MapPresenter
import com.glovo.glovo.map.view.cluster.*
import com.glovo.glovo.util.PermissionCallback
import com.glovo.glovo.util.PermissionManager
import com.glovo.glovo.util.REQUEST_ID_PERMISSIONS_LOCATION
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.PolygonOptions
import com.google.android.material.snackbar.Snackbar
import com.google.maps.android.PolyUtil
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import java.util.ArrayList
import com.google.maps.android.clustering.ClusterManager
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.gms.common.util.MapUtils
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import kotlinx.android.synthetic.main.include_city_details.*
import kotlinx.android.synthetic.main.include_map.*


private val TAG = "TAG${MapActivity::class.java.simpleName}"

class MapActivity : MvpActivity<MainView, MapPresenter>(), MainView, OnMapReadyCallback,
    ClusterManager.OnClusterItemClickListener<CityClusterItem>, PermissionCallback, GoogleMap.OnCameraIdleListener {

    private var allAvailableAreas = ArrayList<List<LatLng>>()

    override val presenter: MapPresenter by inject { parametersOf(this) }
    private val permissionManager: PermissionManager by inject { parametersOf(this) }

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
        mMap.setOnCameraIdleListener(this)
        permissionManager.requestPermission(Manifest.permission.ACCESS_COARSE_LOCATION, REQUEST_ID_PERMISSIONS_LOCATION)

    }


    override fun showLoading() {
        progressBar.visibility = View.VISIBLE

    }


    override fun hideLoading() {
        progressBar.visibility = View.INVISIBLE


    }

    override fun showCountries(countries: List<Country>) {


    }

    override fun showError(error: String) {


    }

    override fun showCities(cities: List<City>) {

        cities.forEach { city ->
            val polygons = ArrayList<PolygonOptions>()
            val polygonOptions = PolygonOptions()

            city.workingArea.forEach { polygonEncoded ->
                polygonOptions.addAll(PolyUtil.decode(polygonEncoded))
                polygons.add(polygonOptions)
            }


            val polygonOption =
                ConvexHull.convert(polygons).strokeColor(ContextCompat.getColor(this, R.color.working_area_stroke_color))
                    .fillColor(ContextCompat.getColor(this, R.color.working_area_color))
            val polygon = mMap.addPolygon(polygonOption)
            mClusterItemManager?.addItem(
                CityClusterItem(
                    polygon?.getCenterPoint()!!,
                    city.name,
                    city.code,
                    city.countryCode, polygon.getBounds()
                )
            )

            allAvailableAreas.add(polygon.points)
        }

    }

    override fun onClusterItemClick(pin: CityClusterItem): Boolean {

        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(pin.bounds, 0))

        presenter.getCityDetails(pin.code)
        return true
    }


    override fun showCityDetails(city: City) {

        cityNameTextView.text = city.currency

    }


    @SuppressLint("MissingPermission")
    override fun onGranted(permission: String) {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.lastLocation.addOnCompleteListener {
            if (it.isSuccessful) {
                val location = LatLng(it.result?.latitude ?: 0.0, it.result?.longitude ?: 0.0)
                mMap.animateCamera(CameraUpdateFactory.newLatLng(location))
            } else {
                // TODO user should select location
            }
        }
    }

    override fun onDenied(permission: String) {

        Snackbar.make(parentView, getString(R.string.enable_location_permission), Snackbar.LENGTH_LONG)
            .setAction(getString(R.string.setting)) {
                val intent = Intent()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                val uri = Uri.fromParts("package", packageName, null)
                intent.data = uri
                startActivity(intent)
            }.show()

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onCameraIdle() {
        mClusterItemManager?.onCameraIdle()
        val camLocationTarget = mMap.cameraPosition.target
        val isWorkingArea = allAvailableAreas.filter {

            PolyUtil.containsLocation(camLocationTarget, it, true)
        }.isNotEmpty()

        if (isWorkingArea) {
            targetLocationPointerImageView.setImageResource(R.drawable.ic_pin_in_working_area_48dp)
        } else {
            targetLocationPointerImageView.setImageResource(R.drawable.ic_pin_out_working_area_48dp)
        }

    }

}

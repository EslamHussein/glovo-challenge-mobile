package com.glovo.glovo.map.view

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
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
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat
import com.glovo.glovo.selectlocation.view.CITY_CODE_ARG
import com.glovo.glovo.selectlocation.view.SELECT_LOCATION_ACTIVITY_RESULT_CODE
import com.glovo.glovo.selectlocation.view.SelectLocationActivity
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.include_city_details.*
import kotlinx.android.synthetic.main.include_map.*


private val TAG = "TAG${MapActivity::class.java.simpleName}"

class MapActivity : MvpActivity<MainView, MapPresenter>(), MainView, OnMapReadyCallback,
    ClusterManager.OnClusterItemClickListener<CityClusterItem>, PermissionCallback, GoogleMap.OnCameraIdleListener {

    private var allAvailableAreas = ArrayList<List<LatLng>>()

    private val citiesClustered = HashMap<String, CityClusterItem>()

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

        Snackbar.make(parentView, error, Snackbar.LENGTH_LONG).show()

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
                ConvexHull.convert(polygons)
                    .strokeColor(ContextCompat.getColor(this, R.color.working_area_stroke_color))
                    .fillColor(ContextCompat.getColor(this, R.color.working_area_color))
            val polygon = mMap.addPolygon(polygonOption)
            val cityClusterItem = CityClusterItem(
                polygon?.getCenterPoint()!!,
                city.name,
                city.code,
                city.countryCode, polygon.getBounds()
            )
            mClusterItemManager?.addItem(cityClusterItem)
            citiesClustered[city.code] = cityClusterItem

            allAvailableAreas.add(polygon.points)
        }

    }

    override fun onClusterItemClick(pin: CityClusterItem?): Boolean {

        if (pin != null) {
            mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(pin.bounds, 0))

            presenter.getCityDetails(pin.code)
            return false

        }
        return true
    }


    override fun showCityDetails(city: City) {

        cityNameTextView.text = city.name
        currencyTextView.text = city.currency
        countryTextView.text = city.countryCode
        timeZoneTextView.text = city.timeZone

    }


    @SuppressLint("MissingPermission")
    override fun onGranted(permission: String) {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.lastLocation.addOnCompleteListener {
            if (it.isSuccessful && it.result != null) {
                val location = LatLng(it.result?.latitude ?: 0.0, it.result?.longitude ?: 0.0)
                mMap.animateCamera(CameraUpdateFactory.newLatLng(location))
            } else {
                showSelectLocation()
            }
        }
    }

    override fun onDenied(permission: String) {

        Snackbar.make(parentView, getString(R.string.enable_location_permission), Snackbar.LENGTH_LONG)
            .setAction(getString(R.string.setting)) {
                navigateToPermissionSetting()
            }.show()

    }

    private fun navigateToPermissionSetting() {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        startActivity(intent)
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

    private fun showSelectLocation() {

        getOkDialog(
            getString(R.string.select_location),
            getString(R.string.select_current_location_dialog_msg),
            getString(R.string.select),
            getString(R.string.cancel)
        ) {
            navigateToSelectLocation()
        }.show()
    }

    private fun navigateToSelectLocation() {
        val intent = Intent(this, SelectLocationActivity::class.java)
        this.startActivityForResult(intent, SELECT_LOCATION_ACTIVITY_RESULT_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SELECT_LOCATION_ACTIVITY_RESULT_CODE) {
            if (resultCode == RESULT_OK) {
                val result = data?.getStringExtra(CITY_CODE_ARG)

                onClusterItemClick(citiesClustered[result])
            }
            if (resultCode == RESULT_CANCELED) {
                showError(getString(R.string.you_have_not_selected_your_location))
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_map_activity, menu);
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {


        return when (item?.itemId) {
            R.id.action_select_location -> {
                navigateToSelectLocation()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }

    }

}

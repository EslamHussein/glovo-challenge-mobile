package com.glovo.glovo.map.view.cluster

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.clustering.ClusterItem

data class CityClusterItem(
    private val mPosition: LatLng,
    val name: String,
    val code: String,
    val countryCode: String,
    val bounds: LatLngBounds
) : ClusterItem {
    override fun getSnippet(): String = code

    override fun getTitle(): String = name

    override fun getPosition(): LatLng = mPosition

}
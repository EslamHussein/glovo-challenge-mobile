package com.glovo.glovo.map.view.cluster

import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Polygon


fun Polygon.getCenterPoint(): LatLng {
    var centerLatLng: LatLng? = null
    val builder = LatLngBounds.Builder()
    for (i in 0 until points.size) {
        builder.include(points[i])
    }
    val bounds = builder.build()
    centerLatLng = bounds.center
    return centerLatLng
}

fun Polygon.getBounds(): LatLngBounds {

    val centerBuilder = LatLngBounds.builder()
    for (point in points) {
        centerBuilder.include(point)
    }
    return centerBuilder.build()

}
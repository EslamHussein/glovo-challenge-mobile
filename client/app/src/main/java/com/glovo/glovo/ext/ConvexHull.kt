package com.glovo.glovo.ext

import com.google.android.gms.common.util.MapUtils
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolygonOptions
import java.util.ArrayList


object ConvexHull {


    fun convert(polygonOptions: List<PolygonOptions>): PolygonOptions {
        val points = ArrayList<LatLng>()

        for (polygon in polygonOptions) {
            points.addAll(polygon.points)
        }

        if (points.size < 3) {
            throw IllegalStateException()
        }

        val pointsResult = ArrayList<LatLng>()


        // Find the leftmost point
        var leftPoint = 0
        points.indices
            .asSequence()
            .filter { points[it].latitude < points[leftPoint].latitude }
            .forEach { leftPoint = it }

        var p = leftPoint
        var q: Int
        do {
            pointsResult.add(points[p])

            q = (p + 1) % points.size

            for (i in points.indices) {
                if (orientation(points[p], points[i], points[q]) == 2) {
                    q = i
                }
            }
            p = q
        } while (p != leftPoint)

        val result = PolygonOptions()

        for (latLng in pointsResult) {
            result.add(latLng)
        }

        for (polygon in polygonOptions) {
            for (hole in polygon.holes) {
                result.addHole(hole)
            }
        }

        return result

    }

    private fun orientation(p: LatLng, q: LatLng, r: LatLng): Int {
        val orientation =
            (q.latitude - p.latitude) * (r.longitude - q.longitude) - (q.longitude - p.longitude) * (r.latitude - q.latitude)

        if (orientation == 0.0) {
            return 0
        }
        return if (orientation > 0) 1 else 2
    }
}







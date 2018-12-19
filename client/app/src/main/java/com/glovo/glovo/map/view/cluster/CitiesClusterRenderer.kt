package com.glovo.glovo.map.view.cluster

import android.content.Context
import androidx.core.content.ContextCompat
import com.glovo.glovo.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer
import com.google.maps.android.ui.IconGenerator


class CitiesClusterRenderer(
    private val context: Context, map: GoogleMap, clusterItemManager: ClusterManager<CityClusterItem>
) : DefaultClusterRenderer<CityClusterItem>(context, map, clusterItemManager) {


    override fun onBeforeClusterItemRendered(item: CityClusterItem?, markerOptions: MarkerOptions?) {
        super.onBeforeClusterItemRendered(item, markerOptions)

        val markerDescriptor: BitmapDescriptor =
            BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW);

        markerOptions?.icon(markerDescriptor)?.snippet(item?.title);

    }

    override fun onBeforeClusterRendered(cluster: Cluster<CityClusterItem>?, markerOptions: MarkerOptions?) {
        super.onBeforeClusterRendered(cluster, markerOptions)

        val mClusterIconGenerator = IconGenerator(context)

        mClusterIconGenerator.setBackground(
            ContextCompat.getDrawable(context, R.drawable.background_circle)
        )
        mClusterIconGenerator.setTextAppearance(R.style.AppTheme_WhiteTextAppearance)
        val icon = mClusterIconGenerator.makeIcon(cluster?.items?.first()?.countryCode)
        markerOptions?.icon(BitmapDescriptorFactory.fromBitmap(icon));


    }



}
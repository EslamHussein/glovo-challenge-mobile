package com.glovo.glovo.map.view.cluster

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.Cluster

class WorkingAreaCluster(private val cityName: String) : Cluster<CityClusterItem> {

    private val mItems: ArrayList<CityClusterItem> = ArrayList()

    fun add(t: CityClusterItem): Boolean {
        return this.mItems.add(t)
    }

    override fun getPosition(): LatLng {
        return this.mItems.first().position
    }

    fun remove(t: CityClusterItem): Boolean {
        return this.mItems.remove(t)
    }

    override fun getItems(): Collection<CityClusterItem> {
        return this.mItems
    }

    override fun getSize(): Int {
        return this.mItems.size
    }


    override fun hashCode(): Int {
        return this.cityName.hashCode() + this.mItems.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return if (other !is CityClusterItem) {
            false
        } else {
            other.title == this.cityName && other.position == this.position
        }
    }


}
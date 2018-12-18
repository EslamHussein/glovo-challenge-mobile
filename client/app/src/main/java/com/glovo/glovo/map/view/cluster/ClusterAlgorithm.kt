package com.glovo.glovo.map.view.cluster

import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.algo.Algorithm
import java.util.*
import kotlin.collections.HashMap


class ClusterAlgorithm : Algorithm<CityClusterItem> {
    private val TAG = ClusterAlgorithm::class.java.simpleName

    private val mItems = Collections.synchronizedSet(HashSet<CityClusterItem>())

    override fun getItems(): MutableCollection<CityClusterItem> {
        return mItems

    }

    override fun removeItem(item: CityClusterItem?) {
        mItems.remove(item)
    }

    override fun clearItems() {
        mItems.clear()
    }

    override fun addItems(items: MutableCollection<CityClusterItem>?) {
        items?.forEach { _ ->
            mItems.addAll(items)
        } // TODO need enhancement
    }

    override fun getClusters(zoom: Double): MutableSet<out Cluster<CityClusterItem>> {

        var resultCluster: HashSet<WorkingAreaCluster>? = HashSet()

        synchronized(mItems) {

            resultCluster = when (zoom) {
                in 0 until 6 -> {
                    clusterBy(mItems) {
                        it.snippet
                    }
                }
                in 7 until 9 -> {
                    clusterBy(mItems) {
                        it.title
                    }
                }
                else -> {
                    clusterBy(mItems) {
                        it.position.toString()
                    }
                }
            }

        }

        return resultCluster!!
    }

    override fun addItem(item: CityClusterItem?) {
        mItems.add(item)
    }

    private fun clusterBy(items: Set<CityClusterItem>, clusterBy: (CityClusterItem) -> String): HashSet<WorkingAreaCluster> {
        val resultCluster: HashSet<WorkingAreaCluster> = HashSet()
        val hashMap = HashMap<String, WorkingAreaCluster>()
        items.forEach {
            val key = clusterBy(it)
            var cluster = hashMap[key]
            if (cluster == null) {
                cluster = WorkingAreaCluster(key)
            }
            cluster.add(it)
            hashMap[key] = cluster
        }
        hashMap.keys.forEach {
            resultCluster.add(hashMap[it]!!)
        }
        return resultCluster
    }


}



package com.glovo.glovo.map.view

import android.os.Bundle
import android.util.Log
import com.glovo.glovo.R
import com.glovo.glovo.base.view.MvpActivity
import com.glovo.glovo.map.data.dto.Country
import com.glovo.glovo.map.presenter.MapPresenter
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class MapActivity : MvpActivity<MainView, MapPresenter>(), MainView {

    private val TAG = MapActivity::class.java.simpleName

    override val presenter: MapPresenter by inject { parametersOf(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter.getCountries()
    }

    override fun showLoading() {
        Log.d(TAG, "showLoading")

    }


    override fun hideLoading() {
        Log.d(TAG, "hideLoading")

    }

    override fun showCountries(countries: List<Country>) {
        Log.d(TAG, "showCountries")


    }

    override fun showError(error: String) {
        Log.d(TAG, "showError")
    }

}

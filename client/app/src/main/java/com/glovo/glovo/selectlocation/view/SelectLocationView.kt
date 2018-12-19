package com.glovo.glovo.selectlocation.view

import com.glovo.glovo.base.view.MvpView
import com.glovo.glovo.base.view.ShowErrorView
import com.glovo.glovo.selectlocation.domain.dto.Locations

interface SelectLocationView : MvpView, ShowErrorView {
    fun showLoading()
    fun hideLoading()
    fun showCountriesCities(locations: Locations)
}